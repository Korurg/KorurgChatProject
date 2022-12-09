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

package korurg.settings.service;

import korurg.settings.domain.model.Settings;
import korurg.settings.domain.model.SettingsId;
import korurg.settings.repository.SettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SettingsService {
    private final SettingsRepository settingsRepository;

    public Optional<Settings> getSettings(String scope, String key) {
        return settingsRepository.findById(new SettingsId(scope, key));
    }

    public Optional<Settings> getSettings(SettingsScopeKey scopeKey) {
        return settingsRepository.findById(SettingsId.builder()
                .scope(scopeKey.getScope())
                .key(scopeKey.getKey())
                .build());
    }

    public Settings saveSettings(String scope, String key, String value) {
        return settingsRepository.save(new Settings(new SettingsId(scope, key), value));
    }

    public Settings saveSettings(Settings settings) {
        return settingsRepository.save(settings);
    }
}