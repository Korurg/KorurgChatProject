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

package korurg.twitch.mapper;

import korurg.twitch.domain.dto.TwitchUserEDTO;
import korurg.twitch.domain.enums.TwitchBroadcasterType;
import korurg.twitch.domain.enums.TwitchUserType;
import korurg.twitch.domain.model.TwitchUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TwitchUserMapper {

    @Mapping(target = "userType", expression = "java(toTwitchUserType(twitchUserEDTO.getUserType()))")
    @Mapping(target = "broadcasterType", expression = "java(toTwitchBroadcasterType(twitchUserEDTO.getBroadcasterType()))")
    TwitchUser toEntity(TwitchUserEDTO twitchUserEDTO);


    default TwitchUserType toTwitchUserType(String value) {
        return value.isEmpty() ? TwitchUserType.COMMON : TwitchUserType.getByCode(value);
    }

    default TwitchBroadcasterType toTwitchBroadcasterType(String value) {
        return value.isEmpty() ? TwitchBroadcasterType.COMMON : TwitchBroadcasterType.getByCode(value);
    }
}