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

package korurg.utils.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SettingsService {

    private Map<Type, Object> settings = new HashMap<>();

    public Object getSettings(Type type) {
        return settings.get(type);
    }

    public void saveSettings(Type type, Object dto) {
        settings.put(type, dto);
        saveToFile();
    }

    public void loadFromFile() {
        ObjectMapper objectMapper = new ObjectMapper();
        Map map;
        try {
             map = objectMapper.readValue(new FileReader("./settings.json"), Map.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveToFile() {

    }
}