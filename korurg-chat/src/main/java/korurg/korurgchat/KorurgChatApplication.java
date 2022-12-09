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

import korurg.utils.service.SettingsService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@Slf4j
@SpringBootApplication(scanBasePackages = "korurg")
public class KorurgChatApplication {

    @SneakyThrows
    public static void main(String[] args) {
        if (Arrays.stream(args).anyMatch(s -> s.equals("ui-mode"))) {

        } else {
            SpringApplication.run(KorurgChatApplication.class, args);

        }

        SettingsService settingsService = new SettingsService();
        settingsService.loadFromFile();

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