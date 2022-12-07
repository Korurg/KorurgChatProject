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

package korurg.twitch.service;

import korurg.twitch.api.TwitchApiFeignClient;
import korurg.twitch.domain.dto.TwitchUsersEDTO;
import korurg.twitch.domain.model.TwitchUser;
import korurg.twitch.mapper.TwitchUserMapper;
import korurg.twitch.repository.TwitchUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TwitchUserService {

    @Value("${twitch.client-id}")
    private String twitchClientId;

    @Value("${twitch.token}")
    private String twitchToken;

    @Value("${korurg-chat.intervals.update-user-info-sec}")
    private Long updateUserInfoIntervalSec;

    private final TwitchUserRepository twitchUserRepository;

    private final TwitchApiFeignClient twitchApiFeignClient;

    private final TwitchUserMapper twitchUserMapper;

    public Optional<TwitchUser> getOrUpdateUserByLogin(String login) {
        return twitchUserRepository.findByLogin(login)
                .filter(twitchUser -> twitchUser.getUpdateDateTime().plusSeconds(updateUserInfoIntervalSec).isAfter(LocalDateTime.now()))
                .or(() -> updateUserByLogin(login));
    }

    public Optional<TwitchUser> updateUserByLogin(String login) {
        TwitchUsersEDTO usersByLogin = twitchApiFeignClient.getUsersByLogin(List.of(login), "Bearer " + twitchToken, twitchClientId);

        TwitchUser newTwitchUser = twitchUserMapper.toEntity(usersByLogin.getTwitchUsers().get(0));
        newTwitchUser = twitchUserRepository.save(newTwitchUser);
        return Optional.ofNullable(newTwitchUser);
    }
}