/*
 * Copyright © 2022 KorurgChat author or authors. All rights reserved.
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

package korurg.korurgchat.controller;

import korurg.korurgchat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.*;

import static java.nio.charset.StandardCharsets.UTF_8;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ResourceLoader resourceLoader;
    private final ChatService chatService;

    @GetMapping("/chat")
    public String chat(Model model) {

//        Resource resource = resourceLoader.getResource("classpath:templates/chat/default/chat.html");
        File resource = new File("./templates/chat/hacker-console/chat.html");
        String html = "";
//        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
        try (Reader reader = new InputStreamReader(new FileInputStream(resource), UTF_8)) {
            html = FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

        model.addAttribute("templateToInject", html);

        return "chat/base-chat-template";
    }
}