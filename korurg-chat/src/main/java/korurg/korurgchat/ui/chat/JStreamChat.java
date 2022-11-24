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

package korurg.korurgchat.ui.chat;

import me.friwi.jcefmaven.CefAppBuilder;
import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.MavenCefAppHandlerAdapter;
import me.friwi.jcefmaven.UnsupportedPlatformException;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.springframework.beans.factory.annotation.Value;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class JStreamChat extends JFrame {

    @Value("${server.port}")
    private int port;

    private CefApp cefApp;
    private CefClient cefClient;
    private CefBrowser cefBrowser;

    private Component browserComponent;

    //    https://github.com/viglucci/app-jcef-example/blob/master/src/main/java/example/simple/SimpleFrameExample.java
//    https://github.com/jcefmaven/jcefsampleapp/blob/master/src/main/java/me/friwi/jcefsampleapp/MainFrame.java
    public JStreamChat() {

        CefAppBuilder cefAppBuilder = new CefAppBuilder();

        cefAppBuilder.getCefSettings().windowless_rendering_enabled = false;

        cefAppBuilder.setAppHandler(new MavenCefAppHandlerAdapter() {
            @Override
            public void stateHasChanged(CefApp.CefAppState state) {
                super.stateHasChanged(state);
            }
        });


        try {
            cefApp = cefAppBuilder.build();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedPlatformException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (CefInitializationException e) {
            throw new RuntimeException(e);
        }

        cefClient = cefApp.createClient();

        cefBrowser = cefClient.createBrowser("localhost:"+port+"/chat", false, false);
        browserComponent = cefBrowser.getUIComponent();

        getContentPane().add(browserComponent);
        pack();
        setSize(800, 600);
        setVisible(true);
    }
}