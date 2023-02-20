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

package korurg.twitch.api.domain.enums;

import java.util.Arrays;

public enum TwitchBroadcasterType {
    AFFILIATE("affiliate"),
    PARTNER("partner"),
    COMMON("common");
    private String code;

    TwitchBroadcasterType(String code) {
        this.code = code;
    }

    public static TwitchBroadcasterType getByCode(String code){
        return Arrays.stream(TwitchBroadcasterType.values())
                .filter(twitchBroadcasterType -> twitchBroadcasterType.code.equals(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException());
    }
}