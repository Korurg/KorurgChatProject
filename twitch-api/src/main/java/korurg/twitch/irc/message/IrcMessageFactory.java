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

import org.springframework.web.socket.TextMessage;


public class IrcMessageFactory {
    public static TextMessage createRequestCapabilitiesMessage(ReqCap... reqCaps) {
        StringBuilder caps = new StringBuilder();

        for (ReqCap reqCap : reqCaps) {
            caps.append(reqCap.value).append(" ");
        }

        return new TextMessage("CAP REQ :" + caps);
    }

    public static TextMessage createNickMessage(String nickname) {
        return new TextMessage("NICK " + nickname);
    }

    public static TextMessage createPassMessage(String token) {
        return new TextMessage("PASS oauth:" + token);
    }

    public static TextMessage createJoinMessage(String room){
        return new TextMessage("JOIN #" + room);
    }

    public enum ReqCap {
        MEMBERSHIP("twitch.tv/membership"),
        TAGS("twitch.tv/tags"),
        COMMANDS("twitch.tv/commands");

        private final String value;

        ReqCap(String value) {
            this.value = value;
        }

        public String getValue() {
            return value;
        }
    }
}