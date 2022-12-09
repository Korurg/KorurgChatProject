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

import korurg.chat.api.domain.dto.Message;
import korurg.chat.api.domain.model.MessageFilter;

import java.util.List;

public class PlainMessageFilterStrategy implements MessageFilterStrategy {
    private static final String WORDS_DELIMITER = "<;;>";
    private final MessageFilter messageFilter;

    public PlainMessageFilterStrategy(MessageFilter messageFilter) {
        this.messageFilter = messageFilter;
    }

    @Override
    public boolean filter(Message message) {
        List<String> banWords = List.of(messageFilter.getRules().get("banWords").split(WORDS_DELIMITER));

        for (String banWord : banWords) {
            if (message.getMessage().contains(banWord)) {
                return false;
            }
        }

        return true;
    }
}