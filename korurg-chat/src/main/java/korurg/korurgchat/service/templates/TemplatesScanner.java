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

import korurg.korurgchat.exception.template.InvalidTemplateFormatException;
import korurg.korurgchat.service.templates.parser.TemplateParserContainer;
import korurg.korurgchat.service.templates.spec.TemplateSpec;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TemplatesScanner {

    private final TemplateParserContainer templateParserContainer;

    public @NotNull List<Template<? extends TemplateSpec>> scan(@NotNull String directoryPath) {

        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {

            List<Template<? extends TemplateSpec>> templates = new ArrayList<>();

            Collection<File> files = FileUtils.listFiles(directory, new String[]{"yaml", "yml"}, true);

            for (File file : files) {
                Template<? extends TemplateSpec> template = null;
                try {
                    template = templateParserContainer.parse(file);
                } catch (InvalidTemplateFormatException e) {
                    log.warn("Invalid template format", e);
                }
                templates.add(template);
            }

            return templates;
        }

        return List.of();
    }

}