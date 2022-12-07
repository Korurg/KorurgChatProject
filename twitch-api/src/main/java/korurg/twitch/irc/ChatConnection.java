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
package korurg.twitch.irc;

import korurg.twitch.irc.message.Message;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public interface ChatConnection {

    Flux<Message> getMessagesFlux();

    void connect(String token, String nickname, String room) throws ExecutionException, InterruptedException, IOException;

    void connect(String room) throws IOException, ExecutionException, InterruptedException;
}