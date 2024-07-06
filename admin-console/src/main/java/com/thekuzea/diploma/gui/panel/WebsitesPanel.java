package com.thekuzea.diploma.gui.panel;

import com.thekuzea.diploma.gui.prompt.AddNewWebsite;
import com.thekuzea.diploma.common.persistence.domain.website.Website;
import com.thekuzea.diploma.common.persistence.domain.website.WebsiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@Component
public class WebsitesPanel extends JPanel {

    private JLabel message;
    private JList<Website> listOfWebsites;
    private DefaultListModel<Website> websitesModel;
    private JScrollPane listScrollPane;

    private JButton addWebsite;
    private JButton removeWebsite;

    @Autowired
    private AddNewWebsite addNewWebsiteFrame;

    private WebsiteRepository websiteRepository;

    public WebsitesPanel(WebsiteRepository websiteRepository) {
        this.websiteRepository = websiteRepository;

        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(250, 320));

        this.add(getMessage());
        this.add(getListScrollPane());
        this.add(getAddWebsite());
        this.add(getRemoveWebsite());
    }

    public void addNewWebsite(Website transmittableWebsite) {
        websitesModel.add(websitesModel.size(), transmittableWebsite);
    }

    public void rearrangeList() {
        websitesModel.clear();

        List<Website> list = websiteRepository.findAll();
        for(int i=0; i != list.size(); ++i) {
            websitesModel.add(i, list.get(i));
        }
    }

    public void rearrangeList(List<Website> list) {
        websitesModel.clear();

        if(list != null) {
            for(int i=0; i != list.size(); ++i) {
                websitesModel.add(i, list.get(i));
            }
        }
    }

    private JLabel getMessage() {
        message = new JLabel("Websites list: ");

        return message;
    }

    private DefaultListModel<Website> getWebsitesModel() {
        websitesModel = new DefaultListModel<>();

        List<Website> tempList = websiteRepository.findAll();
        for(int i=0; i != tempList.size(); ++i) {
            websitesModel.add(i, tempList.get(i));
        }

        return websitesModel;
    }

    private JList<Website> getListOfWebsites() {
        listOfWebsites = new JList<>(getWebsitesModel());
        listOfWebsites.setLayoutOrientation(JList.VERTICAL);

        return listOfWebsites;
    }

    private JScrollPane getListScrollPane() {
        listScrollPane = new JScrollPane(getListOfWebsites());

        listScrollPane.setPreferredSize(new Dimension(230, 300));

        return listScrollPane;
    }

    private JButton getAddWebsite() {
        addWebsite = new JButton("+");

        addWebsite.addActionListener(e -> {
            addNewWebsiteFrame.setVisible(true);
        });

        return addWebsite;
    }

    private JButton getRemoveWebsite() {
        removeWebsite = new JButton("-");

        removeWebsite.addActionListener(e -> {
            if (listOfWebsites.getSelectedIndex() >= 0) {
                Website temp = websitesModel.getElementAt(listOfWebsites.getSelectedIndex());
                websiteRepository.delete(temp);

                websitesModel.remove(listOfWebsites.getSelectedIndex());
            }
        });

        return removeWebsite;
    }
}
