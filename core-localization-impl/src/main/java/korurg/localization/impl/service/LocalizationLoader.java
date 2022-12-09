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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class LocalizationLoader {
    public @NotNull List<Localization> loadLocalizations(@NotNull File directory) {
        List<File> files = new ArrayList<>(FileUtils.listFiles(directory, new String[]{"yaml", "yml"}, true));
        return loadLocalizations(files);
    }

    public @NotNull Optional<Localization> loadLocalization(@NotNull File localizationFile) {
        try {
            String content = FileUtils.readFileToString(localizationFile, StandardCharsets.UTF_8);
            return loadLocalization(content);
        } catch (IOException e) {
            log.warn("Localization file not valid", e);
        }
        return Optional.empty();
    }

    public @NotNull Optional<Localization> loadLocalization(@NotNull String content) {
        try {
            ObjectMapper objectMapper = new ObjectMapper(new YAMLFactory());
            Localization localization = objectMapper.readValue(content, Localization.class);
            return Optional.ofNullable(localization);
        } catch (JsonProcessingException e) {
            log.warn("Localization file not valid", e);
        }
        return Optional.empty();
    }

    public @NotNull List<Localization> loadLocalizations(@NotNull List<File> localizationFiles) {
        List<Localization> localizations = new ArrayList<>();
        for (File file : localizationFiles) {
            Optional<Localization> localization = loadLocalization(file);
            localization.ifPresent(localizations::add);
        }
        return localizations;
    }
}