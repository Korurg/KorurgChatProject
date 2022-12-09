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

package korurg.chat.impl.controller.api;

import korurg.chat.api.domain.model.MessageFilter;
import korurg.chat.impl.domain.dto.MessageFilterVDTO;
import korurg.chat.impl.domain.dto.PlainMessageFilterCDTO;
import korurg.chat.impl.domain.dto.PlainMessageFilterUDTO;
import korurg.chat.impl.domain.dto.PlainMessageFilterVDTO;
import korurg.chat.impl.mapper.MessageFilterMapper;
import korurg.chat.impl.message.filter.MessageFilterFactory;
import korurg.chat.impl.service.MessageFilterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/chat/filter", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class MessageFilterV1Controller {

    private final MessageFilterService messageFilterService;
    private final MessageFilterMapper messageFilterMapper;

    @PostMapping("/plain-filter")
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public PlainMessageFilterVDTO createPlainFilter(@Valid @RequestBody PlainMessageFilterCDTO plainMessageFilter) {
        MessageFilter messageFilter = messageFilterService.saveMessageFilter(messageFilterMapper.fromPlainCDTO(plainMessageFilter));
        return messageFilterMapper.toPlainVDTO(messageFilter);
    }

    @PutMapping("/plain-filter")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public PlainMessageFilterVDTO updatePlainFilter(@Valid @RequestBody PlainMessageFilterUDTO plainMessageFilter) {
        MessageFilter messageFilter = messageFilterService.saveMessageFilter(messageFilterMapper.fromPlainUDTO(plainMessageFilter));
        return messageFilterMapper.toPlainVDTO(messageFilter);
    }

    @GetMapping("/")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<MessageFilterVDTO> getFilters() {
        return messageFilterMapper.toVDTOs(messageFilterService.getMessageFilters());
    }

    @PostMapping("/reload")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public List<MessageFilterVDTO> reload() {
        return messageFilterMapper.toVDTOs(messageFilterService.loadFilters());
    }

}