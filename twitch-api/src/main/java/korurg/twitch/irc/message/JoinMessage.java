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

package korurg.twitch.irc.message;

import korurg.twitch.irc.IrcConnection;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class JoinMessage implements Message {

    private final IrcConnection ircConnection;

    private final String room;

    @Override
    public IrcConnection getIrcConnection() {
        return ircConnection;
    }

    @Override
    public String toString() {
        return "JoinMessage{" +
                "room='" + room + '\'' +
                '}';
    }
}