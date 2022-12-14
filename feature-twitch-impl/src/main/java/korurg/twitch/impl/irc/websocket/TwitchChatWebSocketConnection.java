/*
 * Copyright © 2022-2022 KorurgChat author or authors. All rights reserved.
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

package korurg.twitch.impl.irc.websocket;

import korurg.chat.api.ChatConnection;
import korurg.chat.api.domain.dto.Message;
import korurg.twitch.impl.irc.message.IrcMessageFactory;
import korurg.twitch.impl.irc.message.TwitchMessageConverter;
import korurg.twitch.impl.irc.message.TwitchIrcMessageParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.AbstractWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.io.IOException;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class TwitchChatWebSocketConnection extends TextWebSocketHandler implements WebSocketHandler, ChatConnection {
    private static final String ANONYMOUS_USER = "justinfan" + ThreadLocalRandom.current().nextInt(100, 10000);
    private final String hostname;
    private final int port;
    private final TwitchIrcMessageParser twitchIrcMessageParser;
    private TwitchMessageConverter twitchMessageConverter;

    private String roomName;
    private final AbstractWebSocketClient client;
    private WebSocketSession session;

    private final Sinks.Many<Message> messagesSinks = Sinks.many().replay().limit(50);

    public TwitchChatWebSocketConnection(String hostname,
                                         int port,
                                         String roomName,
                                         AbstractWebSocketClient client,
                                         TwitchIrcMessageParser twitchIrcMessageParser,
                                         TwitchMessageConverter twitchMessageConverter) {
        this.hostname = hostname;
        this.port = port;
        this.roomName = roomName;
        this.client = client;
        this.twitchIrcMessageParser = twitchIrcMessageParser;
        this.twitchMessageConverter = twitchMessageConverter;
    }

    @Override
    public Flux<Message> getMessagesFlux() {
        return messagesSinks.asFlux();
    }

    @Override
    public void connect() throws IOException, ExecutionException, InterruptedException {
        connect(roomName);
    }

    public void connect(String token, String nickname, String room) throws ExecutionException, InterruptedException, IOException {//TODO: change param to token struct
        session = client.doHandshake(this, hostname + ":" + port).get();

        session.sendMessage(IrcMessageFactory.createRequestCapabilitiesMessage(IrcMessageFactory.ReqCap.COMMANDS,
                IrcMessageFactory.ReqCap.TAGS,
                IrcMessageFactory.ReqCap.MEMBERSHIP));

        boolean isAnonymous = token == null || nickname == null;

        if (isAnonymous) {
            session.sendMessage(IrcMessageFactory.createNickMessage(ANONYMOUS_USER));
        } else {
            session.sendMessage(IrcMessageFactory.createPassMessage(token));
            session.sendMessage(IrcMessageFactory.createNickMessage(nickname));
        }

        session.sendMessage(IrcMessageFactory.createJoinMessage(room));
    }

    public void connect(String room) throws IOException, ExecutionException, InterruptedException {
        connect(null, null, room);
    }

    public void sendMessage(String payload) {
        try {
            session.sendMessage(new TextMessage(payload));
        } catch (IOException e) {
            throw new RuntimeException(e);//TODO
        }
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        log.info("raw message: " + message.getPayload());
        twitchIrcMessageParser.parseMessage(message)
                .stream()
                .map(twitchMessageConverter::convert)
                .filter(Objects::nonNull)
                .forEach(messagesSinks::tryEmitNext);
    }

}