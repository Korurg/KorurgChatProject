/*
 * Copyright © 2023 KorurgChat author or authors. All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package korurg.korurgchat.service.templates.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import korurg.korurgchat.exception.template.InvalidTemplateFormatException;
import korurg.korurgchat.service.templates.ApiVersion;
import korurg.korurgchat.service.templates.Template;
import korurg.korurgchat.service.templates.TemplateMetadata;
import korurg.korurgchat.service.templates.TemplateType;
import korurg.korurgchat.service.templates.spec.ChatTemplateSpec;
import korurg.korurgchat.service.templates.spec.TemplateVariable;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ChatTemplateParserV1 implements TemplateParser {

    @Override
    public boolean canParse(File file) {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        try {
            Map map = objectMapper.readValue(file, HashMap.class);
            //TODO: api-version/type/... to constant
            return map.get("api-version").equals(ApiVersion.V1.getValue()) && map.get("type").equals(TemplateType.CHAT.getValue());
        } catch (IOException e) {
            return false;
        }
    }

    @Override
    public Template<ChatTemplateSpec> parse(File file) throws InvalidTemplateFormatException {
        ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
        try {
            Map map = objectMapper.readValue(file, HashMap.class);
            return parse(map, file.getParentFile().getPath());
        } catch (IOException e) {
            throw new InvalidTemplateFormatException(e);
        }
    }

    @Override
    public Template parse(Map map, String directoryPath) throws InvalidTemplateFormatException {
        //TODO: maybe something wrong? maybe create dto
        try {
            Map metadata = (Map) map.get("metadata");
            Map spec = (Map) map.get("spec");

            List<TemplateVariable> templateVariables = new ArrayList<>();
            for (Map templateVariable : (List<Map>) spec.get("templates")) {
                templateVariables.add(new TemplateVariable(
                        (String) templateVariable.get("name"),
                        (String) templateVariable.get("type"),
                        (String) templateVariable.get("default-value")
                ));
            }

            ChatTemplateSpec chatTemplateSpec = new ChatTemplateSpec((String) spec.get("main-file"), templateVariables);
            TemplateMetadata templateMetadata = new TemplateMetadata(
                    (String) metadata.get("author"),
                    (String) metadata.get("version"),
                    (String) metadata.get("name"),
                    (Map) metadata.get("descriptions")
            );

            return new Template<>(chatTemplateSpec, directoryPath, TemplateType.CHAT, templateMetadata);
        } catch (ClassCastException e) {
            throw new InvalidTemplateFormatException(e);
        }
    }
}