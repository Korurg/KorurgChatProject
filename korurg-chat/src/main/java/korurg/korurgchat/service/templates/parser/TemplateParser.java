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

import java.io.File;
import java.util.Map;

public interface TemplateParser {

    boolean canParse(File file);

    Template<? extends TemplateSpec> parse(File file) throws InvalidTemplateFormatException;

    Template<? extends TemplateSpec> parse(Map map, String directoryPath) throws InvalidTemplateFormatException;
}