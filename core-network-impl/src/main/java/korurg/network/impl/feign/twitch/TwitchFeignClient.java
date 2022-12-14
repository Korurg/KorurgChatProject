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

package korurg.network.impl.feign.twitch;

import korurg.network.api.TwitchFeignClientApi;
import korurg.network.api.domain.dto.TwitchUsersEDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "twitch-api", url = "https://api.twitch.tv")
public interface TwitchFeignClient extends TwitchFeignClientApi {
    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/helix/chat/badges")
    String getChannelChatBadges(@RequestParam("broadcaster_id") Long broadcasterId,
                                @RequestHeader("Authorization") String bearer,
                                @RequestHeader("Client-Id") String clientId);

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/helix/users")
    TwitchUsersEDTO getUsersByLogin(@RequestParam("login") List<String> logins,
                                    @RequestHeader("Authorization") String bearer,
                                    @RequestHeader("Client-Id") String clientId);

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "/helix/users")
    TwitchUsersEDTO getUsersById(@RequestParam("id") List<String> ids,
                                 @RequestHeader("Authorization") String bearer,
                                 @RequestHeader("Client-Id") String clientId);
}