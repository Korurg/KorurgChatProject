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
import korurg.korurgchat.service.templates.spec.TemplateSpec;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Template<T extends TemplateSpec> {
    public final T spec;
    public final String directoryPath;
    public final TemplateType templateType;
    public final TemplateMetadata metadata;

    public String draw(TemplateDrawer templateDrawer)  throws TemplateDrawerException {
        return spec.draw(this, templateDrawer);
    }
}