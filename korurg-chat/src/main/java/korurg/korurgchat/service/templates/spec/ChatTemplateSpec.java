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

package korurg.korurgchat.service.templates.spec;

import korurg.korurgchat.exception.template.TemplateDrawerException;
import korurg.korurgchat.service.templates.Template;
import korurg.korurgchat.service.templates.TemplateDrawer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class ChatTemplateSpec implements TemplateSpec {
    public final String mainFile;
    public final List<TemplateVariable> templateVariables;

    @Override
    public String draw(Template template, TemplateDrawer templateDrawer)  throws TemplateDrawerException {
        return templateDrawer.draw(template, this);
    }
}