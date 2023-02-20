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

package korurg.chat.impl.message.handler;

import korurg.chat.api.domain.dto.Message;
import korurg.chat.api.message.handler.MessageHandler;

import java.util.Map;

public class PlainMessageHandler implements MessageHandler {
    @Override
    public boolean isEnabled() {
        return false;
    }

    @Override
    public void setEnabled(boolean value) {

    }

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public boolean filter(Message message) {
        return false;
    }

    @Override
    public Map<String, String> getRules() {
        return null;
    }
}