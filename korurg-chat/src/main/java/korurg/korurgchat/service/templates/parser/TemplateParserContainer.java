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

import korurg.korurgchat.exception.template.InvalidTemplateFormatException;
import korurg.korurgchat.service.templates.Template;
import korurg.korurgchat.service.templates.spec.TemplateSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TemplateParserContainer {

    private final List<TemplateParser> templateParserList;

    public Template<? extends TemplateSpec> parse(File file) throws InvalidTemplateFormatException {
        for (TemplateParser templateParser : templateParserList) {
            if(templateParser.canParse(file)){
                return templateParser.parse(file);
            }
        }
        throw new InvalidTemplateFormatException("No parser was found for this format");
    }

}