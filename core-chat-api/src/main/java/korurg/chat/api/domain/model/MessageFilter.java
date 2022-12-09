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

package korurg.chat.api.domain.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import korurg.chat.api.message.filter.MessageFilterType;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.Map;

@Entity
@Table(name = "message_filters")
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageFilter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "rules")
    private String rawRules;
    @Column(name = "type")
    private MessageFilterType type;

    @Transient
    private Map<String, String> rules;

    @Transient
    public @NotNull Map<String, String> getRules() {
        ObjectMapper objectMapper = new ObjectMapper();
        if (rules == null) {
            try {
                rules = objectMapper.readValue(rawRules, new TypeReference<Map<String, String>>() {});
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        return rules;
    }

    @Transient
    public void updateRules() {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            rawRules = objectMapper.writeValueAsString(rules);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}