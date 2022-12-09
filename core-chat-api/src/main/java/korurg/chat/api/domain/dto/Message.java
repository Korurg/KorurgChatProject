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

package korurg.chat.api.domain.dto;

import korurg.chat.api.Platform;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Builder.Default
    private List<String> badges= new ArrayList<>();
    private Platform platform;
    private String message;
    private String displayName;
    private String userColor;
    @Singular
    private List<UserRole> userRoles = new ArrayList<>();

    public enum UserRole {
        SUBSCRIBER("subscriber_paid"),
        SUBSCRIBER_PAID("subscriber"),
        FOLLOWER("follower"),
        MODERATOR("moderator"),
        STREAMER("streamer");

        private String userRole;

        UserRole(String userRole) {
            this.userRole = userRole;
        }
    }
}