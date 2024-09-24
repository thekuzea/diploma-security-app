package com.thekuzea.diploma.gui.prompt.user;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.thekuzea.diploma.common.persistence.domain.app.App;
import com.thekuzea.diploma.common.persistence.domain.app.AppRepository;
import com.thekuzea.diploma.common.persistence.domain.user.User;
import com.thekuzea.diploma.common.persistence.domain.user.UserRepository;
import com.thekuzea.diploma.common.persistence.domain.website.Website;
import com.thekuzea.diploma.common.persistence.domain.website.WebsiteRepository;

import static com.thekuzea.diploma.gui.constant.ActionButtons.CLEAR;
import static com.thekuzea.diploma.gui.constant.ActionButtons.SUBMIT;
import static com.thekuzea.diploma.gui.constant.ElementDimensions.TEXT_INPUT_COLUMNS_NUMBER;

@Component
@RequiredArgsConstructor
public class EditUserFrame {

    private static final int FRAME_WIDTH = 400;

    private static final int FRAME_HEIGHT = 330;

    private static final int SCROLL_WIDTH = 370;

    private static final int SCROLL_HEIGHT = 95;

    private final UserRepository userRepository;

    private final WebsiteRepository websiteRepository;

    private final AppRepository appRepository;

    private JFrame frame;

    private JTextField usernameText;

    private JList<Website> websiteJList;

    private DefaultListModel<Website> websitesModel;

    private JList<App> appJList;

    private DefaultListModel<App> appsModel;

    private User currentUpdateUser;

    public void setUserAndPrepareWindow(final User user) {
        this.currentUpdateUser = user;

        reloadLists();

        selectIndicesOfWebsiteList();
        selectIndicesOfAppList();

        usernameText.setText(currentUpdateUser.getUsername());
    }

    private void reloadLists() {
        if (websitesModel == null || appsModel == null) {
            return;
        }

        websitesModel.clear();
        final List<Website> websiteList = websiteRepository.findAll();
        for (int i = 0; i < websiteList.size(); i++) {
            websitesModel.add(i, websiteList.get(i));
        }

        appsModel.clear();
        final List<App> appList = appRepository.findAll();
        for (int i = 0; i < appList.size(); i++) {
            appsModel.add(i, appList.get(i));
        }
    }

    private void selectIndicesOfWebsiteList() {
        if (websitesModel == null || currentUpdateUser.getForbiddenWebsites() == null) {
            return;
        }

        final List<Integer> currentUserRestrictedWebsites = new ArrayList<>();
        for (int i = 0; i < websitesModel.size(); i++) {
            for (Website it : currentUpdateUser.getForbiddenWebsites()) {
                if (websitesModel.get(i).equals(it)) {
                    currentUserRestrictedWebsites.add(i);
                }
            }
        }

        final int[] indices = currentUserRestrictedWebsites.stream()
                .mapToInt(i -> i)
                .toArray();
        websiteJList.setSelectedIndices(indices);
    }

    private void selectIndicesOfAppList() {
        if (appsModel == null || currentUpdateUser.getForbiddenApps() == null) {
            return;
        }

        final List<Integer> currentUserRestrictedApps = new ArrayList<>();
        for (int i = 0; i < appsModel.size(); i++) {
            for (App it : currentUpdateUser.getForbiddenApps()) {
                if (appsModel.get(i).equals(it)) {
                    currentUserRestrictedApps.add(i);
                }
            }
        }

        final int[] indices = currentUserRestrictedApps.stream()
                .mapToInt(i -> i)
                .toArray();
        appJList.setSelectedIndices(indices);
    }

    public void createFrame() {
        final JFrame currentFrame = new JFrame();
        currentFrame.setTitle("Edit user");
        currentFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        currentFrame.setLocationRelativeTo(null);
        currentFrame.add(createMainPanel());
        currentFrame.setVisible(true);

        frame = currentFrame;
    }

    private JPanel createMainPanel() {
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        mainPanel.add(createUsernameLabel());
        mainPanel.add(createUsernameText());

        mainPanel.add(createWebsitesLabel());
        mainPanel.add(createWebsiteScrollPane());

        mainPanel.add(createAppsLabel());
        mainPanel.add(createAppScrollPane());

        mainPanel.add(createSubmitButton());
        mainPanel.add(createDeselectEverythingButton());

        return mainPanel;
    }

    private JLabel createUsernameLabel() {
        return new JLabel("Username: ");
    }

    private JTextField createUsernameText() {
        usernameText = new JTextField(TEXT_INPUT_COLUMNS_NUMBER);
        usernameText.setEditable(false);

        return usernameText;
    }

    private JLabel createWebsitesLabel() {
        return new JLabel("Websites list: ");
    }

    private DefaultListModel<Website> createWebsitesModel() {
        websitesModel = new DefaultListModel<>();

        final List<Website> websiteList = websiteRepository.findAll();
        for (int i = 0; i < websiteList.size(); i++) {
            websitesModel.add(i, websiteList.get(i));
        }

        return websitesModel;
    }

    private JList<Website> createWebsiteJList() {
        websiteJList = new JList<>(createWebsitesModel());
        return websiteJList;
    }

    private JScrollPane createWebsiteScrollPane() {
        final JScrollPane websiteScrollPane = new JScrollPane(createWebsiteJList());
        websiteScrollPane.setPreferredSize(new Dimension(SCROLL_WIDTH, SCROLL_HEIGHT));

        return websiteScrollPane;
    }

    private JLabel createAppsLabel() {
        return new JLabel("Applications list: ");
    }

    private DefaultListModel<App> createAppsModel() {
        appsModel = new DefaultListModel<>();

        final List<App> appList = appRepository.findAll();
        for (int i = 0; i < appList.size(); i++) {
            appsModel.add(i, appList.get(i));
        }

        return appsModel;
    }

    private JList<App> createAppJList() {
        appJList = new JList<>(createAppsModel());
        return appJList;
    }

    private JScrollPane createAppScrollPane() {
        final JScrollPane appScrollPane = new JScrollPane(createAppJList());
        appScrollPane.setPreferredSize(new Dimension(SCROLL_WIDTH, SCROLL_HEIGHT));

        return appScrollPane;
    }

    private JButton createSubmitButton() {
        final JButton submit = new JButton(SUBMIT);

        submit.addActionListener(e -> {
            final int[] selectedWebsiteIndices = websiteJList.getSelectedIndices();
            final List<Website> selectedWebsiteList = new ArrayList<>();

            for (final int websiteIndex : selectedWebsiteIndices) {
                selectedWebsiteList.add(websitesModel.getElementAt(websiteIndex));
            }
            currentUpdateUser.setForbiddenWebsites(selectedWebsiteList);

            final int[] selectedAppIndices = appJList.getSelectedIndices();
            final List<App> selectedAppList = new ArrayList<>();

            for (final int appIndex : selectedAppIndices) {
                selectedAppList.add(appsModel.getElementAt(appIndex));
            }
            currentUpdateUser.setForbiddenApps(selectedAppList);

            userRepository.save(currentUpdateUser);

            destroyWindow();
        });

        return submit;
    }

    private JButton createDeselectEverythingButton() {
        final JButton deselectEverything = new JButton(CLEAR);

        deselectEverything.addActionListener(e -> {
            websiteJList.clearSelection();
            appJList.clearSelection();
        });

        return deselectEverything;
    }

    private void destroyWindow() {
        frame.dispose();
    }
}
