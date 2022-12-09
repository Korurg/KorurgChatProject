/*
 * Copyright © 2022-2023 KorurgChat author or authors. All rights reserved.
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

package korurg.korurgchat.controller.ui;

import korurg.korurgchat.exception.template.TemplateDrawerException;
import korurg.korurgchat.service.templates.TemplateDrawer;
import korurg.korurgchat.service.templates.TemplatesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ChatUiController {

    private final TemplatesService templatesService;
    private final TemplateDrawer templateDrawer;

    @GetMapping("/chat")
    public String chat(Model model) throws TemplateDrawerException {
        String html = templateDrawer.draw(templatesService.getCurrentChatTemplate());
        model.addAttribute("templateToInject", html);
        return "chat/base-chat-template";
    }
}