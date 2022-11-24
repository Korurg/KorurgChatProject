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

package korurg.twitch.irc.websocket;

import korurg.twitch.irc.IrcConnection;
import korurg.twitch.irc.message.Message;
import korurg.twitch.irc.message.MessageParser;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Sinks;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class WebSocketIrcConnection extends TextWebSocketHandler implements WebSocketHandler, IrcConnection {

    private String hostname;
    private int port;
    private MessageParser messageParser;

    private StandardWebSocketClient client;
    private WebSocketSession session;

    private final Sinks.Many<Message> messagesSinks = Sinks.many().replay().limit(50);

    public WebSocketIrcConnection(String hostname, int port, MessageParser messageParser) {
        this.hostname = hostname;
        this.port = port;
        this.messageParser = messageParser;
    }

    public Flux<Message> getMessagesFlux() {
        return messagesSinks.asFlux();
    }

    public void connect(String token, String nickname, String room) throws ExecutionException, InterruptedException, IOException {//TODO: change param to token struct
        client = new StandardWebSocketClient();

        session = client.doHandshake(this, hostname + ":" + port).get();
        boolean isAnonymous = token == null || nickname == null;

        session.sendMessage(new TextMessage("CAP REQ :twitch.tv/membership twitch.tv/tags twitch.tv/commands"));

        if (isAnonymous) {
            session.sendMessage(new TextMessage("NICK justinfan" + 1231));//TODO: generate random number
        } else {
            session.sendMessage(new TextMessage("PASS oauth" + token));
            session.sendMessage(new TextMessage("NICK " + nickname));
        }

        session.sendMessage(new TextMessage("JOIN #" + room));
    }

    public void sendMessage() {

    }

    public void connect(String room) throws IOException, ExecutionException, InterruptedException {
        connect(null, null, room);
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) {
        messageParser.parseMessage(message, this).forEach(messagesSinks::tryEmitNext);
    }

}