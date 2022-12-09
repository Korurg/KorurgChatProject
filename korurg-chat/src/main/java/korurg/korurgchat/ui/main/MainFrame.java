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

package korurg.korurgchat.ui.main;

import korurg.localization.impl.service.LocalizationService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
@RequiredArgsConstructor
public class MainFrame extends JFrame {

    private final LocalizationService localizationService;

    public void initFrame() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(500, 600));

        JPanel panel = new JPanel(new GridBagLayout());

        addMainMenuBar(panel);
        addMainTabbedPane(panel);


        panel.setBackground(Color.orange);
        this.setContentPane(panel);
        this.pack();
    }

    private void addMainTabbedPane(JPanel panel) {

    }

    private void addMainMenuBar(JPanel panel) {
        JMenuBar mainMenuBar = new JMenuBar();
        GridBagConstraints mainMenuBarGridBagConstraints = new GridBagConstraints();
        mainMenuBarGridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        mainMenuBarGridBagConstraints.weightx = 1.0;
        mainMenuBarGridBagConstraints.weighty = 0.1;
        mainMenuBarGridBagConstraints.gridx = 0;
        mainMenuBarGridBagConstraints.gridy = 0;
        mainMenuBarGridBagConstraints.anchor = GridBagConstraints.NORTH;

        JMenu fileMenu = new JMenu("File");
        mainMenuBar.add(fileMenu);

        JMenu helpMenu = getHelpMainMenu();
        mainMenuBar.add(helpMenu);

        panel.add(mainMenuBar, mainMenuBarGridBagConstraints);
    }

    @NotNull
    private static JMenu getHelpMainMenu() {
        JMenu helpMenu = new JMenu("Help");

        JMenuItem githubMenuItem = new JMenuItem("Github");
        helpMenu.add(githubMenuItem);

        JMenuItem aboutMenuItem = new JMenuItem("About");
        helpMenu.add(aboutMenuItem);

        return helpMenu;
    }
}