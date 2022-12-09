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

import korurg.korurgchat.service.templates.spec.TemplateSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TemplatesService {

    private final TemplatesScanner templatesScanner;
    private final String DEFAULT_PATH = "./templates";

    private Template<? extends TemplateSpec> currentChatTemplate;

    @PostConstruct
    private void postConstruct() {
        List<Template<? extends TemplateSpec>> scan = scan();
        if (!scan.isEmpty()) {
            currentChatTemplate = scan.get(0);
        }
    }

    public List<Template<? extends TemplateSpec>> scan(String directoryPath) {
        return templatesScanner.scan(directoryPath);
    }

    public List<Template<? extends TemplateSpec>> scan() {
        return scan(DEFAULT_PATH);
    }

    public Template<? extends TemplateSpec> getCurrentChatTemplate() {
        return currentChatTemplate;
    }
}