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

package korurg.korurgchat.mapper;

import korurg.chat.api.ChatConnection;
import korurg.korurgchat.domain.dto.ChatConnectionsVDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ChatConnectionMapper {

    default ChatConnectionsVDTO toChatConnectionsVDTO(List<ChatConnection> chatConnections){
        ChatConnectionsVDTO chatConnectionsVDTO = new ChatConnectionsVDTO();
        chatConnectionsVDTO.setConnections(chatConnections.stream().map(this::toChatConnectionVDTO).toList());
        return chatConnectionsVDTO;
    }

    @Mapping(target = "platform", source = "chatConnection.platform.name")
    @Mapping(target = "uuid", source = "chatConnection.uuid")
    @Mapping(target = "name", source = "chatConnection.name")
    ChatConnectionsVDTO.ChatConnectionVDTO toChatConnectionVDTO(ChatConnection chatConnection);
}