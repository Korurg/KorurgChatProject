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

package korurg.twitch.api.domain.model;

import korurg.twitch.api.domain.enums.TwitchUserType;
import korurg.twitch.api.domain.enums.TwitchBroadcasterType;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "twitch_users")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TwitchUser {
    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "login", nullable = false)
    private String login;

    @Column(name = "display_name")
    private String displayName;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private TwitchUserType userType;

    @Enumerated(EnumType.STRING)
    @Column(name = "broadcaster_type")
    private TwitchBroadcasterType broadcasterType;

    @Column(name = "description")
    private String description;

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @Column(name = "offline_image_url")
    private String offlineImageUrl;

    @Column(name = "created_at")
    private String createdAt;

    @UpdateTimestamp
    @Column(name = "update_date_time")
    private LocalDateTime updateDateTime;
}