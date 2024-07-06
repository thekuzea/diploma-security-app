package com.thekuzea.diploma.gui.frame;

import javax.swing.JFrame;
import javax.swing.JPanel;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.thekuzea.diploma.config.async.AsyncConfig;
import com.thekuzea.diploma.gui.panel.UserAppsInnerPanel;
import com.thekuzea.diploma.gui.panel.UserMainPanel;
import com.thekuzea.diploma.gui.panel.UserWebsitesInnerPanel;
import com.thekuzea.diploma.common.persistence.domain.user.User;
import com.thekuzea.diploma.common.persistence.domain.user.UserRepository;

import static com.thekuzea.diploma.UserApplication.USERNAME;

@Component
@RequiredArgsConstructor
public class UserWindow {

    private static final String USER_WINDOW_NAME = "LocalWall";

    private static final int USER_WINDOW_WIDTH = 630;

    private static final int USER_WINDOW_HEIGHT = 430;

    private static final int DB_SCAN_AND_REDRAW_DELAY_MILLIS = 3000;

    private final UserRepository userRepository;

    private UserWebsitesInnerPanel userWebsitesInnerPanel;

    private UserAppsInnerPanel userAppsInnerPanel;

    private User currentUser;

    @PostConstruct
    public void init() {
        currentUser = userRepository.findByUsername(USERNAME);

        final JFrame userFrame = new JFrame();
        userFrame.setTitle(USER_WINDOW_NAME);
        userFrame.setSize(USER_WINDOW_WIDTH, USER_WINDOW_HEIGHT);
        userFrame.setLocationRelativeTo(null);

        this.userWebsitesInnerPanel = new UserWebsitesInnerPanel(currentUser);
        final JPanel websitesPanel = userWebsitesInnerPanel.createPanel();

        this.userAppsInnerPanel = new UserAppsInnerPanel(currentUser);
        final JPanel appsPanel = userAppsInnerPanel.createPanel();

        final JPanel mainPanel = new UserMainPanel().createMainPanel(websitesPanel, appsPanel);
        userFrame.add(mainPanel);

        userFrame.setVisible(true);
    }

    @Async(AsyncConfig.ASYNC_JOBS_THREAD_POOL_BEAN_BANE)
    @Scheduled(fixedRate = DB_SCAN_AND_REDRAW_DELAY_MILLIS)
    public void redrawLists() {
        currentUser = userRepository.findByUsername(USERNAME);

        userWebsitesInnerPanel.reinitializeModel();
        userAppsInnerPanel.reinitializeModel();
    }
}
