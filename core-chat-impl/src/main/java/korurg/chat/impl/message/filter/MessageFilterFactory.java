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

package korurg.chat.impl.message.filter;

import korurg.chat.api.domain.model.MessageFilter;
import korurg.chat.api.message.filter.MessageFilterType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class MessageFilterFactory {

    public PlainMessageFilterBuilder plainMessageFilterBuilder() {
        return new PlainMessageFilterBuilder();
    }

    public static class PlainMessageFilterBuilder {
        private MessageFilter messageFilter = new MessageFilter();

        private List<String> banWords = new ArrayList<>();

        private String name;
        private Boolean enabled;
        private Long id;

        public PlainMessageFilterBuilder setName(@Nullable String name) {
            this.name = name;
            return this;
        }

        public PlainMessageFilterBuilder setEnabled(@NotNull Boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public PlainMessageFilterBuilder addBanWord(@NotNull String banWord) {
            banWords.add(banWord);
            return this;
        }

        public PlainMessageFilterBuilder addBanWords(@Nullable List<String> banWords) {
            if (banWords != null) {
                this.banWords.addAll(banWords);
            }
            return this;
        }

        public PlainMessageFilterBuilder setId(@Nullable Long id) {
            this.id = id;
            return this;
        }

        public @NotNull MessageFilter build() {
            Map<String, String> rules = new HashMap<>();
            //TODO: to constants
            rules.put("banWords", banWords.stream().reduce("", (s, s2) -> s + (s.isEmpty() ? "" : "<;;>") + s2));

            messageFilter.setRules(rules);
            messageFilter.setName(name);
            messageFilter.setId(id);
            messageFilter.setEnabled(enabled);
            messageFilter.setType(MessageFilterType.PLAIN);

            return messageFilter;
        }
    }
}