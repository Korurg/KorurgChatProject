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

package korurg.korurgchat.controller.api;

import korurg.chat.api.exception.ChatConnectionException;
import korurg.chat.api.exception.ConnectChatException;
import korurg.korurgchat.domain.dto.ChatConnectionsVDTO;
import korurg.korurgchat.mapper.ChatConnectionMapper;
import korurg.korurgchat.service.ChatConnectionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/chat/", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class ChatApiV1Controller {

    private final ChatConnectionService chatService;
    private final ChatConnectionMapper chatConnectionMapper;

    @PostMapping("/connections/twitch/connect/{roomName}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void connectToTwitchChat(@RequestParam(required = false) String nickname,
                                    @RequestParam(required = false) String token,
                                    @PathVariable("roomName") String roomName) throws ConnectChatException {
        chatService.connectToTwitchChat(token, nickname, roomName);
    }

    @GetMapping("/connections")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public ChatConnectionsVDTO getChatConnections() {
        return chatConnectionMapper.toChatConnectionsVDTO(chatService.getChatConnections());
    }

    @PostMapping("/connections/disconnect/{connectionId}")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public void disconnectFromChat(@PathVariable("connectionId") String connectionId) throws ChatConnectionException {
        chatService.disconnect(UUID.fromString(connectionId));
    }
}