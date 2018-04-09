package com.thekuzea.diploma.gui.frame;

import com.thekuzea.diploma.blocker.AppBlocker;
import com.thekuzea.diploma.blocker.WebsiteBlocker;
import com.thekuzea.diploma.model.App;
import com.thekuzea.diploma.model.User;
import com.thekuzea.diploma.model.Website;
import com.thekuzea.diploma.repository.UserRepository;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.List;

import static com.thekuzea.diploma.MainClass.USERNAME;

@Component
public class UserFrame extends JFrame {

    private JPanel mainPanel;
    private JPanel websitesPanel;
    private JPanel appsPanel;

    private JLabel mainLabel;
    private JLabel websitesLabel;
    private JLabel appsLabel;

    private DefaultListModel<Website> websiteModel;
    private JList<Website> listOfWebsites;

    private DefaultListModel<App> appModel;
    private JList<App> listOfApps;

    private JScrollPane websitesScrollPane;
    private JScrollPane appsScrollPane;

    private UserRepository userRepository;
    private User currentUser;

    private Timer timerForLists = new Timer(3000, e -> reloadLists());
    private Timer timerUploadingWebsiteRestrictions = new Timer(3000, e -> {
        WebsiteBlocker.uploadFile(currentUser.getForbiddenWebsites());
    });
    private Timer timerForKillingProcesses;

    public UserFrame(UserRepository userRepository) {
        this.userRepository = userRepository;

        currentUser = this.userRepository.findByUsername(USERNAME);
        timerForKillingProcesses = new Timer(3000, e -> {
            try {
                AppBlocker.parseProcessList(currentUser.getForbiddenApps());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });

        this.setTitle("LocalWall");
        this.setSize(630, 430);
        this.setLocationRelativeTo(null);
        this.add(getMainPanel());

        timerForLists.start();
        timerUploadingWebsiteRestrictions.start();
        timerForKillingProcesses.start();
    }

    private JPanel getMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(getMainLabel(), BorderLayout.NORTH);
        mainPanel.add(getWebsitesPanel(), BorderLayout.WEST);
        mainPanel.add(getAppsPanel(), BorderLayout.EAST);

        return mainPanel;
    }

    private JLabel getMainLabel() {
        mainLabel = new JLabel();
        mainLabel.setText("Lists of blocked services");
        mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainLabel.setPreferredSize(new Dimension(200, 40));

        return mainLabel;
    }

    private JPanel getWebsitesPanel() {
        websitesPanel = new JPanel();
        websitesPanel.setLayout(new FlowLayout());
        websitesPanel.setPreferredSize(new Dimension(300, 320));
        websitesPanel.add(getWebsitesLabel());

        websitesScrollPane = new JScrollPane(getListOfWebsites());
        websitesScrollPane.setPreferredSize(new Dimension(250, 300));
        websitesPanel.add(websitesScrollPane);

        return websitesPanel;
    }

    private JLabel getWebsitesLabel() {
        websitesLabel = new JLabel();
        websitesLabel.setText("Websites list: ");

        return websitesLabel;
    }

    private void initWebsiteModel() {
        List<Website> tempWebsiteList = currentUser.getForbiddenWebsites();
        try {
            for(int i=0; i != tempWebsiteList.size(); ++i) {
                websiteModel.add(i, tempWebsiteList.get(i));
            }
        } catch (NullPointerException ignored) { }
    }

    private JList<Website> getListOfWebsites() {
        websiteModel = new DefaultListModel<>();

        initWebsiteModel();

        listOfWebsites = new JList<>(websiteModel);
        listOfWebsites.setLayoutOrientation(JList.VERTICAL);

        return listOfWebsites;
    }

    private JPanel getAppsPanel() {
        appsPanel = new JPanel();
        appsPanel.setLayout(new FlowLayout());
        appsPanel.setPreferredSize(new Dimension(300, 320));
        appsPanel.add(getAppsLabel());

        appsScrollPane = new JScrollPane(getListOfApps());
        appsScrollPane.setPreferredSize(new Dimension(250, 300));
        appsPanel.add(appsScrollPane);

        return appsPanel;
    }

    private JLabel getAppsLabel() {
        appsLabel = new JLabel();
        appsLabel.setText("Applications list: ");

        return appsLabel;
    }

    private void initAppModel() {
        List<App> tempAppList = currentUser.getForbiddenApps();
        try {
            for(int i=0; i != tempAppList.size(); ++i) {
                appModel.add(i, tempAppList.get(i));
            }
        } catch (NullPointerException ignored) { }
    }

    private JList<App> getListOfApps() {
        appModel = new DefaultListModel<>();

        initAppModel();

        listOfApps = new JList<>(appModel);
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
