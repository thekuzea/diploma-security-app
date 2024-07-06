package com.thekuzea.diploma.gui.frame;

import javax.swing.JFrame;
import javax.swing.JPanel;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.thekuzea.diploma.config.async.AsyncConfig;
import com.thekuzea.diploma.gui.panel.AppsInnerPanel;
import com.thekuzea.diploma.gui.panel.MainPanel;
import com.thekuzea.diploma.gui.panel.WebsitesInnerPanel;
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

    private WebsitesInnerPanel websitesInnerPanel;

    private AppsInnerPanel appsInnerPanel;

    private User currentUser;

    @PostConstruct
    public void init() {
        currentUser = userRepository.findByUsername(USERNAME);

        final JFrame userFrame = new JFrame();
        userFrame.setTitle(USER_WINDOW_NAME);
        userFrame.setSize(USER_WINDOW_WIDTH, USER_WINDOW_HEIGHT);
        userFrame.setLocationRelativeTo(null);

        this.websitesInnerPanel = new WebsitesInnerPanel(currentUser);
        final JPanel websitesPanel = websitesInnerPanel.createPanel();

        this.appsInnerPanel = new AppsInnerPanel(currentUser);
        final JPanel appsPanel = appsInnerPanel.createPanel();

        final JPanel mainPanel = new MainPanel().createMainPanel(websitesPanel, appsPanel);
        userFrame.add(mainPanel);

        userFrame.setVisible(true);
    }

    @Async(AsyncConfig.ASYNC_JOBS_THREAD_POOL_BEAN_BANE)
    @Scheduled(fixedRate = DB_SCAN_AND_REDRAW_DELAY_MILLIS)
    public void redrawLists() {
        currentUser = userRepository.findByUsername(USERNAME);

        websitesInnerPanel.reinitializeModel();
        appsInnerPanel.reinitializeModel();
    }
}
