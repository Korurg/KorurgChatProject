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

package korurg.korurgchat.config;

import korurg.twitch.irc.ChatConnection;
import korurg.twitch.irc.message.TwitchIrcMessageParser;
import korurg.twitch.irc.websocket.TwitchChatWebSocketConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

@Configuration
public class TwitchApiConfig {

    @Bean("twitchChatConnection")
    public ChatConnection getWebsocketIrcConnection() {
        return new TwitchChatWebSocketConnection("wss://irc-ws.chat.twitch.tv",
                443,
                new StandardWebSocketClient(),
                new TwitchIrcMessageParser());
    }
}