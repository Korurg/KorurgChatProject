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

package message.parser;

import korurg.twitch.irc.message.*;
import org.junit.jupiter.api.Test;
import org.springframework.web.socket.TextMessage;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TwitchIrcMessageParserTest {

    @Test
    void testJoinMessage() {
        TwitchIrcMessageParser twitchIrcMessageParser = new TwitchIrcMessageParser();
        List<Message> messages = twitchIrcMessageParser.parseMessage(new TextMessage(":justinfan1231!justinfan1231@justinfan1231.tmi.twitch.tv JOIN #alinarinrin"), null);

        assertEquals(1, messages.size());
        assertInstanceOf(JoinMessage.class, messages.get(0));
    }

    @Test
    void testRoomstateMessage() {
        TwitchIrcMessageParser twitchIrcMessageParser = new TwitchIrcMessageParser();
        List<Message> messages = twitchIrcMessageParser.parseMessage(new TextMessage("@emote-only=0;followers-only=10;r9k=0;" +
                "room-id=114037984;slow=0;subs-only=0 :tmi.twitch.tv ROOMSTATE #alinarinrin"), null);

        assertEquals(1, messages.size());
        assertInstanceOf(RoomstateMessage.class, messages.get(0));
        RoomstateMessage roomstateMessage = (RoomstateMessage) messages.get(0);
        assertFalse(roomstateMessage.isEmoteOnly());
        assertTrue(roomstateMessage.isFollowersOnly());
        assertFalse(roomstateMessage.isSubOnly());
        assertFalse(roomstateMessage.isR9k());
        assertEquals(114037984, roomstateMessage.getRoomId());
    }

    @Test
    void testCommonMessage() {
        String rawMessage = "@badge-info=;badges=glitchcon2020/1;client-nonce=96a7f85c320ca44bac2525ca964e385a;" +
                "color=#9ACD32;display-name=BeachBum511;emotes=;first-msg=0;flags=;" +
                "id=3cf53744-d9cc-4cdc-b19a-dfdaae567a6d;mod=0;returning-chatter=0;room-id=114037984;subscriber=0;" +
                "tmi-sent-ts=1669145309523;turbo=0;user-id=414365041;user-type= " +
                ":beachbum511!beachbum511@beachbum511.tmi.twitch.tv PRIVMSG #alinarinrin :monkaHmm";

        TwitchIrcMessageParser twitchIrcMessageParser = new TwitchIrcMessageParser();
        List<Message> messages = twitchIrcMessageParser.parseMessage(new TextMessage(rawMessage), null);

        assertEquals(1, messages.size());
        assertInstanceOf(CommonMessage.class, messages.get(0));
        CommonMessage commonMessage = (CommonMessage) messages.get(0);
        //TODO: check when develope common message
    }

}