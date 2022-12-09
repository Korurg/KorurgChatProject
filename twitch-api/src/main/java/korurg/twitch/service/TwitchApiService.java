/*
 * Copyright © 2022 KorurgChat author or authors. All rights reserved.
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

package korurg.twitch.service;

import korurg.twitch.api.TwitchApiFeignClient;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TwitchApiService {

    private final TwitchApiFeignClient twitchApiFeignClient;

    @Value("${twitch.client-id}")
    private String twitchClientId;

//    @Value("${twitch.token}")
    private String twitchToken;

    public void auth() {
        String uri = "https://id.twitch.tv/oauth2/authorize?response_type=token" +
                "&client_id=" + twitchClientId +
                "&redirect_uri=http://localhost:8083/twitch/auth" +
                "&scope=channel%3Amanage%3Apolls+channel%3Aread%3Apolls";

        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {


            try {
                Desktop.getDesktop().browse(new URI(uri));
            } catch (IOException e) {
                throw new RuntimeException(e);
            } catch (URISyntaxException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                String cmd = "google-chrome-stable " + uri + ""; //TODO: add support for other platforms
                Runtime.getRuntime().exec(cmd);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
//    public String getUser(List<String> logins) {
//        return twitchApiFeignClient.getUsersByLogin(logins, twitchToken, twitchClientId);
//    }
}