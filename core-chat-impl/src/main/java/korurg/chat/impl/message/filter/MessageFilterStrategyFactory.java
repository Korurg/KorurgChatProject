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
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Component;

@Component
public class MessageFilterStrategyFactory {

    public MessageFilterStrategy getStrategy(MessageFilter messageFilter) {
        switch (messageFilter.getType()) {
            case PLAIN -> {
                return new PlainMessageFilterStrategy(messageFilter);
            }
        }
        throw new NotImplementedException("Cannot create MessageFilterStrategy for MessageFilter with type %s".formatted(messageFilter.getType().getName()));
    }
}