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

package korurg.twitch.impl.irc.message;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;

@Getter
@Builder
@RequiredArgsConstructor
public class CommonMessageTwitch implements TwitchChatMessage {


    private final String user;

    private final String userColor;
    private final String text;

    private final String badgeInfo;

    private final List<Badge> badges;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CommonMessageTwitch that = (CommonMessageTwitch) o;

        if (isFirstMessage != that.isFirstMessage) return false;
        if (isMod != that.isMod) return false;
        if (isReturningChatter != that.isReturningChatter) return false;
        if (roomId != that.roomId) return false;
        if (isSubscriber != that.isSubscriber) return false;
        if (isTurbo != that.isTurbo) return false;
        if (userId != that.userId) return false;
        if (isVip != that.isVip) return false;
        if (!Objects.equals(user, that.user)) return false;
        if (!Objects.equals(userColor, that.userColor)) return false;
        if (!Objects.equals(text, that.text)) return false;
        if (!Objects.equals(badgeInfo, that.badgeInfo)) return false;
        if (!Objects.equals(badges, that.badges)) return false;
        if (!Objects.equals(clientNonce, that.clientNonce)) return false;
        if (!Objects.equals(displayName, that.displayName)) return false;
        if (!Objects.equals(emotes, that.emotes)) return false;
        if (!Objects.equals(flags, that.flags)) return false;
        return Objects.equals(userType, that.userType);
    }

    @Override
    public int hashCode() {
        int result = user != null ? user.hashCode() : 0;
        result = 31 * result + (userColor != null ? userColor.hashCode() : 0);
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (badgeInfo != null ? badgeInfo.hashCode() : 0);
        result = 31 * result + (badges != null ? badges.hashCode() : 0);
        result = 31 * result + (clientNonce != null ? clientNonce.hashCode() : 0);
        result = 31 * result + (displayName != null ? displayName.hashCode() : 0);
        result = 31 * result + (emotes != null ? emotes.hashCode() : 0);
        result = 31 * result + (isFirstMessage ? 1 : 0);
        result = 31 * result + (flags != null ? flags.hashCode() : 0);
        result = 31 * result + (isMod ? 1 : 0);
        result = 31 * result + (isReturningChatter ? 1 : 0);
        result = 31 * result + (int) (roomId ^ (roomId >>> 32));
        result = 31 * result + (isSubscriber ? 1 : 0);
        result = 31 * result + (isTurbo ? 1 : 0);
        result = 31 * result + (int) (userId ^ (userId >>> 32));
        result = 31 * result + (isVip ? 1 : 0);
        result = 31 * result + (userType != null ? userType.hashCode() : 0);
        return result;
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

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class Badge {
        private final String badge;
        private final String version;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Badge badge1 = (Badge) o;

            if (!Objects.equals(badge, badge1.badge)) return false;
            return Objects.equals(version, badge1.version);
        }

        @Override
        public int hashCode() {
            int result = badge != null ? badge.hashCode() : 0;
            result = 31 * result + (version != null ? version.hashCode() : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Badge{" +
                    "badge='" + badge + '\'' +
                    ", version='" + version + '\'' +
                    '}';
        }
    }
}