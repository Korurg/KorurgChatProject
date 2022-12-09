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
import korurg.chat.api.Platform;
import korurg.chat.api.domain.dto.Message;
import korurg.chat.api.exception.ChatConnectionException;
import korurg.chat.api.exception.ConnectChatException;
import korurg.twitch.impl.irc.message.IrcMessageFactory;
import korurg.twitch.impl.irc.message.TwitchIrcMessageParser;
import korurg.twitch.impl.irc.message.TwitchMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.AbstractWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.io.IOException;
import java.nio.channels.UnresolvedAddressException;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
public class TwitchChatWebSocketConnection extends TextWebSocketHandler implements WebSocketHandler, ChatConnection {
    private static final String ANONYMOUS_USER = "justinfan" + ThreadLocalRandom.current().nextInt(100, 10000);
    private final UUID uuid = UUID.randomUUID();
    private final String hostname;
    private final int port;
    private final TwitchIrcMessageParser twitchIrcMessageParser;
    private final TwitchMessageConverter twitchMessageConverter;
    private final AbstractWebSocketClient client;
    private WebSocketSession session;
    private Sinks.Many<Message> messagesSinks;
    private String room;

    public TwitchChatWebSocketConnection(@NotNull  String hostname,
                                         int port,
                                         @NotNull AbstractWebSocketClient client,
                                         @NotNull TwitchIrcMessageParser twitchIrcMessageParser,
                                         @NotNull TwitchMessageConverter twitchMessageConverter) {
        this.hostname = hostname;
        this.port = port;
        this.client = client;
        this.twitchIrcMessageParser = twitchIrcMessageParser;
        this.twitchMessageConverter = twitchMessageConverter;
    }

    @Override
    public @NotNull UUID getUuid() {
        return uuid;
    }

    @Override
    public @NotNull Platform getPlatform() {
        return Platform.TWITCH;
    }

    @Override
    public @Nullable String getName() {
        return room;
    }

    @Override
    public @NotNull Flux<Message> getMessagesFlux() {
        return messagesSinks.asFlux();
    }

    public void connect(@Nullable String token,
                        @Nullable String nickname,
                        @NotNull String room) throws ConnectChatException {
        try {
            messagesSinks =  Sinks.many().replay().limit(50);
            session = client.doHandshake(this, hostname + ":" + port).get();

            sendMessage(IrcMessageFactory.createRequestCapabilitiesMessage(IrcMessageFactory.ReqCap.COMMANDS,
                    IrcMessageFactory.ReqCap.TAGS,
                    IrcMessageFactory.ReqCap.MEMBERSHIP));

            boolean isAnonymous = token == null || nickname == null;

            if (isAnonymous) {
                sendMessage(IrcMessageFactory.createNickMessage(ANONYMOUS_USER));
            } else {
                sendMessage(IrcMessageFactory.createPassMessage(token));
                sendMessage(IrcMessageFactory.createNickMessage(nickname));
            }

            this.room = room;

            sendMessage(IrcMessageFactory.createJoinMessage(room));
        } catch (UnresolvedAddressException | ChatConnectionException |ExecutionException e) {
            throw new ConnectChatException(e);
        } catch (InterruptedException e) {
            log.warn("Interrupt", e);
            Thread.currentThread().interrupt();
        }
    }

    public void connect(@NotNull String room) throws ConnectChatException {
        connect(null, null, room);
    }

    public void sendMessage(@NotNull String payload) throws ChatConnectionException {
        try {
            session.sendMessage(new TextMessage(payload));
        } catch (IOException e) {
            throw new ChatConnectionException(e);
        }
    }

    public void sendMessage(@NotNull TextMessage message) throws ChatConnectionException{
        try {
            session.sendMessage(message);
            log.debug("send message: " + message.getPayload());
        } catch (IOException e) {
            throw new ChatConnectionException(e);
        }
    }

    @Override
    protected void handleTextMessage(@Nullable WebSocketSession session, TextMessage message) {
        log.debug("raw message: " + message.getPayload());
        twitchIrcMessageParser.parseMessage(message)
                .stream()
                .map(twitchMessageConverter::convert)
                .filter(Objects::nonNull)
                .forEach(messagesSinks::tryEmitNext);
    }

    @Override
    public void close() throws ChatConnectionException{
        try {
            messagesSinks.tryEmitComplete();
            session.close();
            this.room = null;
        } catch (IOException e) {
            throw new ChatConnectionException(e);
        }
    }
}