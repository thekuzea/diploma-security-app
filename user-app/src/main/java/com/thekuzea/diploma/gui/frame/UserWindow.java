package com.thekuzea.diploma.gui.frame;

import java.util.concurrent.TimeUnit;
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
import com.thekuzea.diploma.helper.user.CurrentUserService;

@Component
@RequiredArgsConstructor
public class UserWindow {

    private static final String USER_WINDOW_NAME = "LocalWall";

    private static final int USER_WINDOW_WIDTH = 630;

    private static final int USER_WINDOW_HEIGHT = 430;

    private final UserRepository userRepository;

    private final CurrentUserService currentUserService;

    private WebsitesInnerPanel websitesInnerPanel;

    private AppsInnerPanel appsInnerPanel;

    @PostConstruct
    public void init() {
        final String currentUsername = currentUserService.getCurrentUser();
        final User currentUser = userRepository.findByUsername(currentUsername);

        final JFrame userFrame = new JFrame();
        userFrame.setTitle(USER_WINDOW_NAME);
        userFrame.setSize(USER_WINDOW_WIDTH, USER_WINDOW_HEIGHT);
        userFrame.setLocationRelativeTo(null);

        websitesInnerPanel = new WebsitesInnerPanel();
        final JPanel websitesPanel = websitesInnerPanel.createPanel(currentUser);

        appsInnerPanel = new AppsInnerPanel();
        final JPanel appsPanel = appsInnerPanel.createPanel(currentUser);

        final JPanel mainPanel = new MainPanel().createMainPanel(websitesPanel, appsPanel);
        userFrame.add(mainPanel);

        userFrame.setVisible(true);
    }

    @Async(AsyncConfig.ASYNC_JOBS_THREAD_POOL_BEAN_BANE)
    @Scheduled(fixedDelayString = "${local-wall.db-scan-and-redraw-delay-seconds}", timeUnit = TimeUnit.SECONDS)
    public void redrawLists() {
        final String currentUsername = currentUserService.getCurrentUser();
        final User currentUser = userRepository.findByUsername(currentUsername);

        websitesInnerPanel.reinitializeModel(currentUser);
        appsInnerPanel.reinitializeModel(currentUser);
    }
}
