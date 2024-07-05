package com.thekuzea.diploma.gui.user.frame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.thekuzea.diploma.model.App;
import com.thekuzea.diploma.model.User;
import com.thekuzea.diploma.model.Website;
import com.thekuzea.diploma.repository.UserRepository;
import com.thekuzea.diploma.restrict.AppRestrictingUtils;
import com.thekuzea.diploma.restrict.WebsiteRestrictingUtils;

import static com.thekuzea.diploma.MainClass.USERNAME;

@Component
@RequiredArgsConstructor
public class UserFrame extends JFrame {

    private static final String USER_WINDOW_NAME = "LocalWall";

    private static final int USER_WINDOW_WIDTH = 630;

    private static final int USER_WINDOW_HEIGHT = 430;

    private static final int TIMER_DELAY_MILLIS = 3000;

    private final transient UserRepository userRepository;

    private DefaultListModel<Website> websiteModel;

    private DefaultListModel<App> appModel;

    private User currentUser;

    private Timer timerForLists = new Timer(TIMER_DELAY_MILLIS, e -> reloadLists());

    private Timer timerUploadingWebsiteRestrictions = new Timer(
            TIMER_DELAY_MILLIS,
            e -> WebsiteRestrictingUtils.updateHostsFileOnDemand(currentUser.getForbiddenWebsites())
    );

    @PostConstruct
    public void init() {
        currentUser = userRepository.findByUsername(USERNAME);
        Timer timerForKillingProcesses = new Timer(TIMER_DELAY_MILLIS, e -> {
            AppRestrictingUtils.queryProcessListAndKillOnDemand(currentUser.getForbiddenApps());
        });

        this.setTitle(USER_WINDOW_NAME);
        this.setSize(USER_WINDOW_WIDTH, USER_WINDOW_HEIGHT);
        this.setLocationRelativeTo(null);
        this.add(getMainPanel());

        timerForLists.start();
        timerUploadingWebsiteRestrictions.start();
        timerForKillingProcesses.start();
    }

    private JPanel getMainPanel() {
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(getMainLabel(), BorderLayout.NORTH);
        mainPanel.add(getWebsitesPanel(), BorderLayout.WEST);
        mainPanel.add(getAppsPanel(), BorderLayout.EAST);

        return mainPanel;
    }

    private JLabel getMainLabel() {
        final JLabel mainLabel = new JLabel();
        mainLabel.setText("Lists of blocked services");
        mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainLabel.setPreferredSize(new Dimension(200, 40));

        return mainLabel;
    }

    private JPanel getWebsitesPanel() {
        final JPanel websitesPanel = new JPanel();
        websitesPanel.setLayout(new FlowLayout());
        websitesPanel.setPreferredSize(new Dimension(300, 320));
        websitesPanel.add(getWebsitesLabel());

        final JScrollPane websitesScrollPane = new JScrollPane(getListOfWebsites());
        websitesScrollPane.setPreferredSize(new Dimension(250, 300));
        websitesPanel.add(websitesScrollPane);

        return websitesPanel;
    }

    private JLabel getWebsitesLabel() {
        final JLabel websitesLabel = new JLabel();
        websitesLabel.setText("Websites list: ");

        return websitesLabel;
    }

    private void initWebsiteModel() {
        List<Website> tempWebsiteList = currentUser.getForbiddenWebsites();
        try {
            for (int i = 0; i != tempWebsiteList.size(); ++i) {
                websiteModel.add(i, tempWebsiteList.get(i));
            }
        } catch (NullPointerException ignored) {
        }
    }

    private JList<Website> getListOfWebsites() {
        websiteModel = new DefaultListModel<>();

        initWebsiteModel();

        final JList<Website> listOfWebsites = new JList<>(websiteModel);
        listOfWebsites.setLayoutOrientation(JList.VERTICAL);

        return listOfWebsites;
    }

    private JPanel getAppsPanel() {
        final JPanel appsPanel = new JPanel();
        appsPanel.setLayout(new FlowLayout());
        appsPanel.setPreferredSize(new Dimension(300, 320));
        appsPanel.add(getAppsLabel());

        final JScrollPane appsScrollPane = new JScrollPane(getListOfApps());
        appsScrollPane.setPreferredSize(new Dimension(250, 300));
        appsPanel.add(appsScrollPane);

        return appsPanel;
    }

    private JLabel getAppsLabel() {
        final JLabel appsLabel = new JLabel();
        appsLabel.setText("Applications list: ");

        return appsLabel;
    }

    private void initAppModel() {
        List<App> tempAppList = currentUser.getForbiddenApps();
        try {
            for (int i = 0; i != tempAppList.size(); ++i) {
                appModel.add(i, tempAppList.get(i));
            }
        } catch (NullPointerException ignored) {
        }
    }

    private JList<App> getListOfApps() {
        appModel = new DefaultListModel<>();

        initAppModel();

        final JList<App> listOfApps = new JList<>(appModel);
        listOfApps.setLayoutOrientation(JList.VERTICAL);

        return listOfApps;
    }

    private void reloadLists() {
        currentUser = this.userRepository.findByUsername(USERNAME);

        websiteModel.clear();
        initWebsiteModel();

        appModel.clear();
        initAppModel();
    }
}
