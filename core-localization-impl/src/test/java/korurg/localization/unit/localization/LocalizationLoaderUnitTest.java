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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class LocalizationLoaderUnitTest {
    private static final String CONTENT = """
            author: Korurg
            name: official Russian translation
            description: official Russian translation
            version: 1.0.0
            translation:
              ui:
                common:
                  yes: "да"
                  no: "нет"
                  back: "назад"
                  cancel: "отмена"
                            """;

    @TempDir
    static Path tempDir;

    @BeforeAll
    static void beforeAll() throws IOException {
        Path localization1FilePath = tempDir.resolve("localization1.yaml");
        Path localization2FilePath = tempDir.resolve("localization2.yaml");
        Files.write(localization1FilePath, CONTENT.getBytes());
        Files.write(localization2FilePath, "".getBytes());
    }

    @Test
    void test_loadLocalization_when_localization_content_exists() {
        LocalizationLoader localizationLoader = new LocalizationLoader();

        Optional<Localization> localizationOpt = localizationLoader.loadLocalization(CONTENT);
        assertThat(localizationOpt).isPresent();
        Localization localization = localizationOpt.get();

        assertThat(localization.getName()).isEqualTo("official Russian translation");
        assertThat(localization.getDescription()).isEqualTo("official Russian translation");
        assertThat(localization.getVersion()).isEqualTo("1.0.0");
    }

    @Test
    void test_loadLocalization_when_localization_content_not_exists() {
        LocalizationLoader localizationLoader = new LocalizationLoader();
        Optional<Localization> localizationOpt = localizationLoader.loadLocalization("");

        assertThat(localizationOpt).isEmpty();
    }

    @Test
    void test_loadLocalizations() {
        LocalizationLoader localizationLoader = new LocalizationLoader();
        List<Localization> localizations = localizationLoader.loadLocalizations(tempDir.toFile());

        assertThat(localizations).hasSize(1);

        Localization localization = localizations.get(0);

        assertThat(localization.getName()).isEqualTo("official Russian translation");
        assertThat(localization.getDescription()).isEqualTo("official Russian translation");
        assertThat(localization.getVersion()).isEqualTo("1.0.0");
    }
}