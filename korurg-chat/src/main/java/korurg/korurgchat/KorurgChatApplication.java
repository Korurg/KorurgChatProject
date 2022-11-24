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

package korurg.korurgchat;

import korurg.korurgchat.ui.chat.JStreamChat;
import korurg.twitch.irc.message.MessageParser;
import korurg.twitch.irc.websocket.WebSocketIrcConnection;
import lombok.SneakyThrows;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@SpringBootApplication(scanBasePackages = "korurg")
public class KorurgChatApplication {

    @SneakyThrows
    public static void main(String[] args) {
        SpringApplication.run(KorurgChatApplication.class, args);

//        ConfigurableApplicationContext context = new SpringApplicationBuilder(KorurgChatApplication.class)
//                .headless(false)
//                .run(args);
//
//        EventQueue.invokeLater(() -> {
//            JStreamChat jStreamChat = new JStreamChat();
//
//            WebSocketIrcConnection webSocketIrcConnection = new WebSocketIrcConnection("wss://irc-ws.chat.twitch.tv", 443, new MessageParser());
//            try {
//                webSocketIrcConnection.connect("juice");
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            } catch (ExecutionException e) {
//                throw new RuntimeException(e);
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//
//
//            webSocketIrcConnection.getMessagesFlux().subscribe(textMessage -> {
//                System.out.println(textMessage);
//            });
//        });
    }
}