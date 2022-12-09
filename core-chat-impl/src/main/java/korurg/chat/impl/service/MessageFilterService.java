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

package korurg.chat.impl.service;

import korurg.chat.api.domain.dto.Message;
import korurg.chat.api.domain.model.MessageFilter;
import korurg.chat.impl.message.filter.MessageFilterStrategy;
import korurg.chat.impl.message.filter.MessageFilterStrategyFactory;
import korurg.chat.impl.repository.MessageFilterRepository;
import korurg.exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageFilterService {

    private final MessageFilterRepository messageFilterRepository;


    private final MessageFilterStrategyFactory messageFilterStrategyFactory;

    private List<MessageFilterStrategy> messageFilterStrategies = new ArrayList<>();
    private List<MessageFilter> messageFilters = new ArrayList<>();

    public List<MessageFilter> getMessageFilters() {
        if (messageFilters.isEmpty()) {
            return loadFilters();
        }
        return messageFilters;
    }

    public List<MessageFilter> loadFilters() {
        messageFilters = messageFilterRepository.findAll();
        messageFilterStrategies = new ArrayList<>();
        for (MessageFilter messageFilter : messageFilters) {
            messageFilterStrategies.add(messageFilterStrategyFactory.getStrategy(messageFilter));
        }
        return messageFilters;
    }

    public void setEnabled(@NotNull Long filterId, boolean enabled) {
        for (int i = 0; i < messageFilters.size(); i++) {
            MessageFilter messageFilter = messageFilters.get(i);
            if (messageFilter.getId().equals(filterId)) {
                messageFilter.setEnabled(enabled);
                messageFilters.set(i, messageFilterRepository.save(messageFilter));
                break;
            }
        }
    }

    public boolean filter(@NotNull Message message) {
        for (MessageFilterStrategy messageFilterStrategy : messageFilterStrategies) {
            if (!messageFilterStrategy.filter(message)) {
                return false;
            }
        }
        return true;
    }

    public MessageFilter saveMessageFilter(MessageFilter messageFilter) {
        if (messageFilter.getId() != null) {
            Optional<MessageFilter> byId = messageFilterRepository.findById(messageFilter.getId());
            if(byId.isEmpty()){
                throw new EntityNotFoundException("Message filter with id %d not found".formatted(messageFilter.getId()));
            }
        }
        messageFilter.updateRules();
        return messageFilterRepository.save(messageFilter);
    }

    public void removeMessageFilter(MessageFilter messageFilter) {
        messageFilterRepository.delete(messageFilter);
    }


}