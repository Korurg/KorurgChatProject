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

package korurg.korurgchat.service;

import korurg.korurgchat.domain.dto.ChatMessageVDTO;
import korurg.twitch.irc.ChatConnection;
import korurg.twitch.irc.message.CommonMessage;
import korurg.twitch.irc.message.Message;
import korurg.twitch.service.TwitchApiService;
import korurg.twitch.service.TwitchUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    @Qualifier("twitchChatConnection")
    private final ChatConnection twitchChatConnection;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final TwitchApiService twitchApiService;
    private final TwitchUserService twitchUserService;
    private final ServletContext context;

    @PostConstruct
    private void postConstruct() {
        connectToChats("rekvizit8bit");
    }

    private void accept(Message message) {
        if (message instanceof CommonMessage commonMessage) {
            ChatMessageVDTO chatMessageVDTO = ChatMessageVDTO.builder()
                    .message(commonMessage.getText())
                    .user(commonMessage.getDisplayName())
                    .userColor(commonMessage.getUserColor())
                    .build();

            simpMessagingTemplate.convertAndSend("/topic/messages", chatMessageVDTO);
        }
    }

    public void connectToChats(String twitchChat) {
        try {
            twitchChatConnection.connect(twitchChat);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        twitchChatConnection.getMessagesFlux().subscribe(this::accept);
    }
}