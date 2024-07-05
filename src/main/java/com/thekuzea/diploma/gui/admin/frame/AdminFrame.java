package com.thekuzea.diploma.gui.admin.frame;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.thekuzea.diploma.gui.admin.panel.AppsPanel;
import com.thekuzea.diploma.gui.admin.panel.UsersPanel;
import com.thekuzea.diploma.gui.admin.panel.WebsitesPanel;

@Component
@RequiredArgsConstructor
public class AdminFrame extends JFrame {

    private static final String ADMIN_WINDOW_NAME = "LocalWall: Admin Panel";

    private static final int ADMIN_WINDOW_WIDTH = 770;

    private static final int ADMIN_WINDOW_HEIGHT = 400;

    private final UsersPanel usersPanel;

    private final WebsitesPanel websitesPanel;

    private final AppsPanel appsPanel;

    @PostConstruct
    public void init() {
        this.setTitle(ADMIN_WINDOW_NAME);
        this.setSize(ADMIN_WINDOW_WIDTH, ADMIN_WINDOW_HEIGHT);
        this.setLocationRelativeTo(null);
        this.add(getMainPanel());
    }

    private JPanel getMainPanel() {
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(usersPanel, BorderLayout.WEST);
        mainPanel.add(websitesPanel, BorderLayout.CENTER);
        mainPanel.add(appsPanel, BorderLayout.EAST);

        return mainPanel;
    }
}
