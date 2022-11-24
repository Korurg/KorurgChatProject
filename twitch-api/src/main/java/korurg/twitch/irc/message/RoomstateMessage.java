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
public class RoomstateMessage implements Message {

    private final boolean isEmoteOnly;
    private final boolean isFollowersOnly;
    private final boolean isR9k;
    private final long roomId;
    private final int slow;
    private final boolean isSubOnly;

    private final IrcConnection ircConnection;

    @Override
    public IrcConnection getIrcConnection() {
        return ircConnection;
    }

    @Override
    public String toString() {
        return "RoomstateMessage{" +
                "isEmoteOnly=" + isEmoteOnly +
                ", isFollowersOnly=" + isFollowersOnly +
                ", isR9k=" + isR9k +
                ", roomId=" + roomId +
                ", slow=" + slow +
                ", isSubOnly=" + isSubOnly +
                '}';
    }
}