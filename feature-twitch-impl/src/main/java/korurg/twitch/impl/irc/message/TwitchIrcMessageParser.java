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

package korurg.twitch.impl.irc.message;

import org.springframework.web.socket.TextMessage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TwitchIrcMessageParser {
    public List<TwitchChatMessage> parseMessage(TextMessage textMessage) {
        try {
            List<String> rawMessages = List.of(textMessage.getPayload().split("\r\n"));
            List<TwitchChatMessage> messages = new ArrayList<>();

            rawMessages.forEach(rawMsg -> {
                String rawMessage = rawMsg;
                String rawTagsComponent = "";


                if (rawMessage.charAt(0) == '@') {
                    int spaceIndex = rawMessage.indexOf(' ');
                    rawTagsComponent = rawMessage.substring(1, spaceIndex);
                    rawMessage = rawMessage.substring(spaceIndex + 1);
                }

                rawMessage = rawMessage.substring(1);

                int colonIndex = rawMessage.indexOf(':');

                String rawCommandComponent = "";
                String rawMessageComponent = "";
                if (colonIndex == -1) {
                    rawCommandComponent = rawMessage;
                } else {
                    rawCommandComponent = rawMessage.substring(0, colonIndex);
                    rawMessageComponent = rawMessage.substring(colonIndex + 1);
                }


                String command = rawCommandComponent.split(" ")[1];

                TwitchChatMessage message = switch (command) {
                    case "JOIN" -> parseJoinMessage(rawCommandComponent);
                    case "PRIVMSG" -> parsePrivMessage(rawTagsComponent, rawCommandComponent, rawMessageComponent);
                    case "ROOMSTATE" -> parseRoomstateMessage(rawTagsComponent);


                    default -> UnknownMessageTwitch.builder()
                            .payload(rawMsg)
                            .build();
                };

                messages.add(message);
            });
            return messages;
        } catch (Exception ex) {
            System.out.println("Cannot parse : " + textMessage.getPayload()); //TODO: change to logger
            ex.printStackTrace();
        }
        return List.of(UnknownMessageTwitch.builder()
                .build());
    }

    private TwitchChatMessage parseRoomstateMessage(String rawTagsComponent) {
        Map<String, String> tags = getTags(rawTagsComponent);

        return RoomstateMessageTwitch.builder()
                .isEmoteOnly(tags.get("emote-only").equals("1"))
                .isSubOnly(tags.get("subs-only").equals("1"))
                .isFollowersOnly(!tags.get("followers-only").equals("-1"))
                .isR9k(tags.get("r9k").equals("1"))
                .slow(Integer.parseInt(tags.get("slow")))
                .roomId(Long.parseLong(tags.get("room-id")))
                .build();
    }

    private Map<String, String> getTags(String rawTagsComponent) {
        return Arrays.stream(rawTagsComponent.split(";"))
                .collect(Collectors.toMap(s -> s.split("=")[0],
                        o -> {
                            String[] split = o.split("=");
                            return split.length > 1 ? split[1] : "";
                        }));
    }


    private TwitchChatMessage parseJoinMessage(String rawCommandComponent) {
        String[] rawCommandComponents = rawCommandComponent.split(" ");

        return JoinMessageTwitch.builder()
                .room(rawCommandComponents[2])
                .build();
    }

    private TwitchChatMessage parsePrivMessage(String rawTagsComponent,
                                               String rawCommandComponent,
                                               String rawMessageComponent) {

        Map<String, String> tags = getTags(rawTagsComponent);

        String[] rawCommandComponents = rawCommandComponent.split(" ");

        return CommonMessageTwitch.builder()
                .user(rawCommandComponents[0].split("!")[0])
                .userId(Long.parseLong(tags.get("user-id")))
                .userColor(tags.get("color"))
                .displayName(tags.get("display-name"))
                .text(rawMessageComponent)
                .isMod(tags.get("mod").equals("1"))
                .isVip(tags.containsValue("vip") && !tags.get("vip").equals("0"))
                .badgeInfo(tags.get("badge-info"))
                .badges(Arrays.stream(tags.get("badges").split(","))
                        .filter(s -> !s.isBlank())
                        .map(rawBadge ->
                        {
                            String[] badgeInfo = rawBadge.split("/");
                            return CommonMessageTwitch.Badge.builder()
                                    .badge(badgeInfo[0])
                                    .version(badgeInfo[1])
                                    .build();
                        }).toList())
                .isReturningChatter(!tags.get("returning-chatter").equals("0"))
                .isTurbo(tags.get("turbo").equals("1"))
                .build();
        //TODO:fill other fields
    }

}