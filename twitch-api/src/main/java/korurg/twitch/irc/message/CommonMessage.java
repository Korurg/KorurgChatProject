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

package korurg.twitch.irc.message;

import korurg.twitch.irc.ChatConnection;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@Builder
@RequiredArgsConstructor
public class CommonMessage implements Message {

    private final ChatConnection chatConnection;

    private final String user;

    private final String userColor;
    private final String text;

    private final String badgeInfo;

    private final String badges;

    private final String clientNonce;

    private final String displayName;

    private final String emotes;

    private final boolean isFirstMessage;

    private final String flags;

    private final boolean isMod;

    private final boolean isReturningChatter;

    private final long roomId;

    private final boolean isSubscriber;

    private final boolean isTurbo;

    private final long userId;

    private final boolean isVip;

    private final String userType;

    public ChatConnection getChatConnection() {
        return chatConnection;
    }

    @Override
    public String toString() {
        return "CommonMessage{" +
                "user='" + user + '\'' +
                ", userColor='" + userColor + '\'' +
                ", text='" + text + '\'' +
                ", badgeInfo='" + badgeInfo + '\'' +
                ", badges='" + badges + '\'' +
                ", clientNonce='" + clientNonce + '\'' +
                ", displayName='" + displayName + '\'' +
                ", emotes='" + emotes + '\'' +
                ", isFirstMessage=" + isFirstMessage +
                ", flags='" + flags + '\'' +
                ", isMod=" + isMod +
                ", isReturningChatter=" + isReturningChatter +
                ", roomId=" + roomId +
                ", isSubscriber=" + isSubscriber +
                ", isTurbo=" + isTurbo +
                ", userId=" + userId +
                ", isVip=" + isVip +
                ", userType='" + userType + '\'' +
                '}';
    }
}