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

package korurg.chat.impl.mapper;

import korurg.chat.api.domain.model.MessageFilter;
import korurg.chat.impl.domain.dto.MessageFilterVDTO;
import korurg.chat.impl.domain.dto.PlainMessageFilterCDTO;
import korurg.chat.impl.domain.dto.PlainMessageFilterUDTO;
import korurg.chat.impl.domain.dto.PlainMessageFilterVDTO;
import korurg.chat.impl.message.filter.MessageFilterFactory;
import org.jetbrains.annotations.NotNull;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring",
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class MessageFilterMapper {

    @Autowired
    private MessageFilterFactory messageFilterFactory;

    @Mapping(target = "rules", source = "rawRules")
    public abstract PlainMessageFilterVDTO toPlainVDTO(@NotNull MessageFilter messageFilter);

    public MessageFilter fromPlainCDTO(@NotNull PlainMessageFilterCDTO messageFilter) {
        return messageFilterFactory.plainMessageFilterBuilder()
                .addBanWords(messageFilter.getBanWords())
                .setEnabled(messageFilter.getEnabled())
                .setName(messageFilter.getName())
                .build();
    }

    public MessageFilter fromPlainUDTO(@NotNull PlainMessageFilterUDTO messageFilter) {
        return messageFilterFactory.plainMessageFilterBuilder()
                .addBanWords(messageFilter.getBanWords())
                .setEnabled(messageFilter.getEnabled())
                .setName(messageFilter.getName())
                .setId(messageFilter.getId())
                .build();
    }

    @Mapping(target = "rules", source = "rawRules")
    public abstract MessageFilterVDTO toVDTO(@NotNull MessageFilter messageFilter);

    public List<MessageFilterVDTO> toVDTOs(@NotNull List<MessageFilter> messageFilters) {
        return messageFilters.stream().map(this::toVDTO).toList();
    }
}