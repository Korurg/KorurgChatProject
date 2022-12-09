/*
 * Copyright © 2022-2022 KorurgChat author or authors. All rights reserved.
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
package korurg.chat.api;

import korurg.chat.api.domain.dto.Message;
import korurg.chat.api.exception.ChatConnectionException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import reactor.core.publisher.Flux;

import java.util.UUID;

public interface ChatConnection {
    @NotNull UUID getUuid();

    @NotNull Platform getPlatform();
    @Nullable String getName();

    @NotNull Flux<Message> getMessagesFlux();

    void close() throws ChatConnectionException;
}