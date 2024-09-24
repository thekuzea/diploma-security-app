package com.thekuzea.diploma.gui.panel;

import java.awt.BorderLayout;
import javax.swing.JPanel;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.thekuzea.diploma.gui.panel.restriction.AppsPanel;
import com.thekuzea.diploma.gui.panel.restriction.WebsitesPanel;
import com.thekuzea.diploma.gui.panel.user.UsersPanel;

@Component
@RequiredArgsConstructor
public class MainPanel {

    private final UsersPanel usersPanel;

    private final WebsitesPanel websitesPanel;

    private final AppsPanel appsPanel;

    public JPanel createMainPanel() {
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(usersPanel.createPanel(), BorderLayout.WEST);
        mainPanel.add(websitesPanel.createPanel(), BorderLayout.CENTER);
        mainPanel.add(appsPanel.createPanel(), BorderLayout.EAST);

        return mainPanel;
    }
}
