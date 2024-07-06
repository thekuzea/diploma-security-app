package com.thekuzea.diploma.gui.prompt;

import com.thekuzea.diploma.common.persistence.domain.app.App;
import com.thekuzea.diploma.common.persistence.domain.user.User;
import com.thekuzea.diploma.common.persistence.domain.website.Website;
import com.thekuzea.diploma.common.persistence.domain.app.AppRepository;
import com.thekuzea.diploma.common.persistence.domain.user.UserRepository;
import com.thekuzea.diploma.common.persistence.domain.website.WebsiteRepository;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class EditUser extends JFrame {

    private JPanel mainPanel;

    private JLabel usernameLabel;
    private JTextField usernameText;

    private JLabel websitesLabel;
    private JList<Website> websiteJList;
    private DefaultListModel<Website> websitesModel;
    private JScrollPane websiteScrollPane;

    private JLabel appsLabel;
    private JList<App> appJList;
    private DefaultListModel<App> appsModel;
    private JScrollPane appScrollPane;

    private JButton submit;
    private JButton deselectEverything;

    private UserRepository userRepository;
    private WebsiteRepository websiteRepository;
    private AppRepository appRepository;

    private User transmittableUser;

    public EditUser(UserRepository userRepository, WebsiteRepository websiteRepository, AppRepository appRepository) {
        this.userRepository = userRepository;
        this.websiteRepository = websiteRepository;
        this.appRepository = appRepository;

        this.setTitle("Edit user");
        this.setSize(400, 330);
        this.setLocationRelativeTo(null);
        this.add(getMainPanel());
    }

    public void transmitUser(User user) {
        this.transmittableUser = user;

        reloadLists();

        selectIndicesOfWebsiteList();
        selectIndicesOfAppList();

        usernameText.setText(transmittableUser.getUsername());
        usernameText.setEditable(false);
    }

    private void reloadLists() {
        websitesModel.clear();
        List<Website> tempWebsiteList = websiteRepository.findAll();
        for(int i=0; i != tempWebsiteList.size(); ++i) {
            websitesModel.add(i, tempWebsiteList.get(i));
        }

        appsModel.clear();
        List<App> tempAppList = appRepository.findAll();
        for(int i=0; i != tempAppList.size(); ++i) {
            appsModel.add(i, tempAppList.get(i));
        }
    }

    private void selectIndicesOfWebsiteList() {
        try {
            List<Integer> tempListOfIndices = new ArrayList<>();
            for(int i=0; i != websitesModel.size(); ++i) {
                for(Website it : transmittableUser.getForbiddenWebsites()) {
                    if(websitesModel.get(i).equals(it)) {
                        tempListOfIndices.add(i);
                    }
                }
            }

            int[] indices = tempListOfIndices
                    .stream()
                    .mapToInt(i->i)
                    .toArray();
            websiteJList.setSelectedIndices(indices);
        } catch (NullPointerException ignored) { }
    }

    private void selectIndicesOfAppList() {
        try {
            List<Integer> tempListOfIndices = new ArrayList<>();
            for(int i=0; i != appsModel.size(); ++i) {
                for(App it : transmittableUser.getForbiddenApps()) {
                    if(appsModel.get(i).equals(it)) {
                        tempListOfIndices.add(i);
                    }
                }
            }

            int[] indices = tempListOfIndices
                    .stream()
                    .mapToInt(i->i)
                    .toArray();
            appJList.setSelectedIndices(indices);
        } catch (NullPointerException ignored) { }
    }

    private JPanel getMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        mainPanel.add(getUsernameLabel());
        mainPanel.add(getUsernameText());

        mainPanel.add(getWebsitesLabel());
        mainPanel.add(getWebsiteScrollPane());

        mainPanel.add(getAppsLabel());
        mainPanel.add(getAppScrollPane());

        mainPanel.add(getSubmit());
        mainPanel.add(getDeselectEverything());

        return mainPanel;
    }

    private JLabel getUsernameLabel() {
        usernameLabel = new JLabel("Username: ");
        return usernameLabel;
    }

    private JTextField getUsernameText() {
        usernameText = new JTextField(20);
        return usernameText;
    }

    private JLabel getWebsitesLabel() {
        websitesLabel = new JLabel("Websites list: ");
        return websitesLabel;
    }

    private DefaultListModel<Website> getWebsitesModel() {
        websitesModel = new DefaultListModel<>();

        List<Website> tempList = websiteRepository.findAll();
        for(int i=0; i != tempList.size(); ++i) {
            websitesModel.add(i, tempList.get(i));
        }

        return websitesModel;
    }

    private JList<Website> getWebsiteJList() {
        websiteJList = new JList<>(getWebsitesModel());
        return websiteJList;
    }

    private JScrollPane getWebsiteScrollPane() {
        websiteScrollPane = new JScrollPane(getWebsiteJList());
        websiteScrollPane.setPreferredSize(new Dimension(370, 95));

        return websiteScrollPane;
    }

    private JLabel getAppsLabel() {
        appsLabel = new JLabel("Applications list: ");
        return appsLabel;
    }

    private DefaultListModel<App> getAppsModel() {
        appsModel = new DefaultListModel<>();

        List<App> tempList = appRepository.findAll();
        for(int i=0; i != tempList.size(); ++i) {
            appsModel.add(i, tempList.get(i));
        }

        return appsModel;
    }

    private JList<App> getAppJList() {
        appJList = new JList<>(getAppsModel());
        return appJList;
    }

    private JScrollPane getAppScrollPane() {
        appScrollPane = new JScrollPane(getAppJList());
        appScrollPane.setPreferredSize(new Dimension(370, 95));

        return appScrollPane;
    }

    private JButton getSubmit() {
        submit = new JButton("Submit");

        submit.addActionListener(e -> {
            int[] websiteIndices = websiteJList.getSelectedIndices();
            List<Website> tempWebsitesList = new ArrayList<>();

            for(int i=0; i != websiteIndices.length; ++i) {
                tempWebsitesList.add(websitesModel.getElementAt(websiteIndices[i]));
            }
            transmittableUser.setForbiddenWebsites(tempWebsitesList);

            int[] appIndices = appJList.getSelectedIndices();
            List<App> tempAppList = new ArrayList<>();

            for(int i=0; i != appIndices.length; ++i) {
                tempAppList.add(appsModel.getElementAt(appIndices[i]));
            }
            transmittableUser.setForbiddenApps(tempAppList);

            userRepository.save(transmittableUser);

            usernameText.setText("");
            this.setVisible(false);
        });

        return submit;
    }

    private JButton getDeselectEverything() {
        deselectEverything = new JButton("Clear");

        deselectEverything.addActionListener(e -> {
            websiteJList.clearSelection();
            appJList.clearSelection();
        });

        return deselectEverything;
    }
 }
