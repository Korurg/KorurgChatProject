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

package korurg.korurgchat.service;

import korurg.chat.api.ChatConnection;
import korurg.chat.api.domain.dto.Message;
import korurg.chat.api.exception.ChatConnectionException;
import korurg.chat.api.exception.ConnectChatException;
import korurg.chat.impl.repository.MessageFilterRepository;
import korurg.chat.impl.service.MessageFilterService;
import korurg.korurgchat.domain.dto.ChatMessageVDTO;
import korurg.twitch.impl.irc.message.TwitchIrcMessageParser;
import korurg.twitch.impl.irc.message.TwitchMessageConverter;
import korurg.twitch.impl.irc.websocket.TwitchChatWebSocketConnection;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.util.*;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class ChatConnectionService {

    private Map<UUID, ChatConnection> activeConnections = new HashMap<>();

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final MessageFilterService messageFilterService;
    private final MessageFilterRepository messageFilterRepository;

    private void accept(@NotNull Message message) {

        if (!messageFilterService.filter(message)) {
            return;
        }

        ChatMessageVDTO chatMessageVDTO = ChatMessageVDTO.builder()
                .message(message.getMessage())
                .user(message.getDisplayName())
                .userColor(message.getUserColor())
                .badges(message.getBadges())
                .platform(message.getPlatform().name())
                .roles(message.getUserRoles().stream().map(Enum::name).toList())
                .build();

        simpMessagingTemplate.convertAndSend("/topic/messages", chatMessageVDTO);
    }

    public ChatConnection connectToTwitchChat(@Nullable String token,
                                              @Nullable String nickname,
                                              @NotNull String roomName) throws ConnectChatException {


        //TODO: wss to settings and init to factory
        TwitchChatWebSocketConnection twitchChatWebSocketConnection = new TwitchChatWebSocketConnection("wss://irc-ws.chat.twitch.tv",
                443,
                new StandardWebSocketClient(),
                new TwitchIrcMessageParser(),
                new TwitchMessageConverter());

        twitchChatWebSocketConnection.connect(token, nickname, roomName);
        twitchChatWebSocketConnection.getMessagesFlux().subscribe(this::accept);
        activeConnections.put(twitchChatWebSocketConnection.getUuid(), twitchChatWebSocketConnection);

        return twitchChatWebSocketConnection;
    }

    public boolean disconnect(UUID connectionUuid) throws ChatConnectionException {
        if (activeConnections.containsKey(connectionUuid)) {
            ChatConnection chatConnection = activeConnections.get(connectionUuid);
            chatConnection.close();
            activeConnections.remove(connectionUuid);
            return true;
        }
        return false;
    }

    public List<ChatConnection> getChatConnections() {
        return new ArrayList<>(activeConnections.values());
    }
}