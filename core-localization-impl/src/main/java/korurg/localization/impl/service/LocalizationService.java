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

import korurg.settings.service.SettingsService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LocalizationService {

    private final SettingsService settingsService;
    private final LocalizationLoader localizationLoader;
    private List<Localization> localizations = new ArrayList<>();
    private Localization currentLocalization;

    public @NotNull List<Localization> loadLocalizations(@NotNull File directory) {
        localizations = localizationLoader.loadLocalizations(directory);
        return new ArrayList<>(localizations);
    }

    public @NotNull List<Localization> loadLocalizations() {
        return loadLocalizations(new File("./localizations"));
    }

    public @NotNull String getLocalization(@NotNull String path) {
        //TODO: maybe redesign
        if (currentLocalization != null) {
            String[] splitedPath = path.split("\\.");

            Map<String, Object> translation = currentLocalization.getTranslation();

            try {
                for (int i = 0; i < splitedPath.length; i++) {
                    Object o = translation.get(splitedPath[i]);
                    if (i != splitedPath.length - 1) {
                        if (o instanceof Map tr) {
                            translation = tr;
                        } else {
                            return path;
                        }
                    } else {
                        if (o instanceof String result) {
                            return result;
                        }
                    }
                }
            } catch (ClassCastException e) {
                return path;
            }
        }
        return path;
    }

    public void setLocalization(@NotNull String id) {
        for (Localization localization : localizations) {
            if (id.equals(localization.getId())) {
                currentLocalization = localization;
                break;
            }
        }
    }

    public static class LocalizationData {
        private LocalizationData() {
        }

        public static class Ui {
            private Ui() {
            }

            public static class Common {
                private Common() {
                }

                public static final String COMMON_PREFIX = "UI.COMMON.";

                public static final String YES = COMMON_PREFIX + "YES";
                public static final String NO = COMMON_PREFIX + "NO";
                public static final String CANCEL = COMMON_PREFIX + "CANCEL";
                public static final String BACK = COMMON_PREFIX + "BACK";
            }

        }
    }
}