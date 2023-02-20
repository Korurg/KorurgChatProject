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

package korurg.localization.unit.localization;

import korurg.localization.impl.service.Localization;
import korurg.localization.impl.service.LocalizationLoader;
import korurg.localization.impl.service.LocalizationService;
import korurg.settings.service.SettingsService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.File;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class LocalizationServiceUnitTest {
    static SettingsService settingsService = Mockito.mock(SettingsService.class);
    static LocalizationLoader localizationLoader = Mockito.mock(LocalizationLoader.class);

    @BeforeAll
    static void beforeAll() {
        Localization localization1 = Localization.builder()
                .description("description1")
                .name("name1")
                .author("author1")
                .translation(Map.of("test-string-1", "test-string-translation-1"
                        , "test-map-1", Map.of("test-string-2", "test-string-translation-2")))
                .build();

        Mockito.when(localizationLoader.loadLocalizations(Mockito.any(File.class))).thenReturn(List.of(
                localization1
        ));
    }

    @Test
    void test_getLocalization_when_path_exists() {
        LocalizationService localizationService = new LocalizationService(settingsService, localizationLoader);
        List<Localization> localizations = localizationService.loadLocalizations();

        assertThat(localizations).hasSize(1);

        localizationService.setLocalization(localizations.get(0).getId());

        assertThat(localizationService.getLocalization("test-string-1")).isEqualTo("test-string-translation-1");
        assertThat(localizationService.getLocalization("test-map-1.test-string-2")).isEqualTo("test-string-translation-2");
    }

    @Test
    void test_getLocalization_when_path_not_exists() {
        LocalizationService localizationService = new LocalizationService(settingsService, localizationLoader);
        List<Localization> localizations = localizationService.loadLocalizations();

        assertThat(localizations).hasSize(1);

        localizationService.setLocalization(localizations.get(0).getId());

        assertThat(localizationService.getLocalization("test-string-2")).isEqualTo("test-string-2");
        assertThat(localizationService.getLocalization("test-map-2.test-string-3")).isEqualTo("test-map-2.test-string-3");
    }
}