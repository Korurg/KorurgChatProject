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

package korurg.korurgchat.service.templates;

import korurg.korurgchat.exception.template.TemplateDrawerException;
import korurg.korurgchat.service.templates.spec.ChatTemplateSpec;
import korurg.korurgchat.service.templates.spec.TemplateVariable;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.*;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
@RequiredArgsConstructor
//TODO: rename
public class TemplateDrawer {
    public String draw(Template template) throws TemplateDrawerException {
        return template.draw(this);
    }

    public String draw(Template template, ChatTemplateSpec chatTemplateSpec) throws TemplateDrawerException {
        File resource = new File(template.directoryPath, chatTemplateSpec.mainFile);
        try {
            String html = FileUtils.readFileToString(resource, UTF_8);

            html = fillTemplateVariables(html, chatTemplateSpec.templateVariables);
            html = fixSrc(html, template.directoryPath);

            return html;
        } catch (IOException e) {
            throw new TemplateDrawerException(e);
        }
    }

    private String fillTemplateVariables(String html, List<TemplateVariable> templateVariables) {
        for (TemplateVariable templateVariable : templateVariables) {
            html = html.replace("{{" + templateVariable.getName() + "}}", templateVariable.getDefaultValue());
        }
        return html;
    }

    private String fixSrc(String html, String path) {
        return html.replace("src=\"", "src=\"" + path.replace("./templates", "/resources") + "/");
    }

}