package com.thekuzea.diploma.gui.frame;

import javax.swing.JFrame;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.thekuzea.diploma.gui.panel.MainPanel;

@Component
@RequiredArgsConstructor
public class AdminWindow {

    private static final String ADMIN_WINDOW_NAME = "LocalWall: Admin Panel";

    private static final int ADMIN_WINDOW_WIDTH = 770;

    private static final int ADMIN_WINDOW_HEIGHT = 400;

    private final MainPanel mainPanel;

    @PostConstruct
    public void init() {
        final JFrame adminFrame = new JFrame();
        adminFrame.setTitle(ADMIN_WINDOW_NAME);
        adminFrame.setSize(ADMIN_WINDOW_WIDTH, ADMIN_WINDOW_HEIGHT);
        adminFrame.setLocationRelativeTo(null);
        adminFrame.add(mainPanel.createMainPanel());
        adminFrame.setVisible(true);
    }
}
