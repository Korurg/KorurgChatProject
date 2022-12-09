/*
 * Copyright © 2023 KorurgChat author or authors. All rights reserved.
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

package korurg.localization.impl.service;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.zip.CRC32;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Localization {

    private String author;
    private String name;
    private String description;
    private String version;

    private Map<String, Object> translation;

    private String id;

    public @NotNull String getId() {
        if (id == null) {
            CRC32 crc32 = new CRC32();
            crc32.update((author + name + version).getBytes());
            id = String.valueOf(crc32.getValue());
        }

        return id;
    }
}