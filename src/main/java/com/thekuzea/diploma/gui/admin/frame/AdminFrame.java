package com.thekuzea.diploma.gui.admin.frame;

import com.thekuzea.diploma.gui.admin.panel.AppsPanel;
import com.thekuzea.diploma.gui.admin.panel.UsersPanel;
import com.thekuzea.diploma.gui.admin.panel.WebsitesPanel;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class AdminFrame extends JFrame {

    private JPanel mainPanel;

    private UsersPanel usersPanel;
    private WebsitesPanel websitesPanel;
    private AppsPanel appsPanel;

    public AdminFrame(UsersPanel usersPanel, WebsitesPanel websitesPanel, AppsPanel appsPanel) {
        this.usersPanel = usersPanel;
        this.websitesPanel = websitesPanel;
        this.appsPanel = appsPanel;

        this.setTitle("LocalWall: Admin Panel");
        this.setSize(770, 400);
        this.setLocationRelativeTo(null);
        this.add(getMainPanel());
    }

    private JPanel getMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(usersPanel, BorderLayout.WEST);
        mainPanel.add(websitesPanel, BorderLayout.CENTER);
        mainPanel.add(appsPanel, BorderLayout.EAST);

        return mainPanel;
    }
}
