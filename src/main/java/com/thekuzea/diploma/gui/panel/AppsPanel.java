package com.thekuzea.diploma.gui.panel;

import com.thekuzea.diploma.gui.prompt.AddNewApp;
import com.thekuzea.diploma.model.App;
import com.thekuzea.diploma.repository.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@Component
public class AppsPanel extends JPanel {

    private JLabel message;
    private JList<App> listOfApps;
    private DefaultListModel<App> appsModel;
    private JScrollPane listScrollPane;

    private JButton addApp;
    private JButton removeApp;

    @Autowired
    private AddNewApp addNewAppFrame;

    private AppRepository appRepository;

    public AppsPanel(AppRepository appRepository) {
        this.appRepository = appRepository;

        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(250, 320));

        this.add(getMessage());
        this.add(getListScrollPane());
        this.add(getAddWebsite());
        this.add(getRemoveWebsite());
    }

    public void addNewApp(App transmittableApp) {
        appsModel.add(appsModel.size(), transmittableApp);
    }

    public void rearrangeList() {
        appsModel.clear();

        List<App> list = appRepository.findAll();
        for(int i=0; i != list.size(); ++i) {
            appsModel.add(i, list.get(i));
        }
    }

    public void rearrangeList(List<App> list) {
        appsModel.clear();

        if(list != null) {
            for(int i=0; i != list.size(); ++i) {
                appsModel.add(i, list.get(i));
            }
        }
    }

    private JLabel getMessage() {
        message = new JLabel("Applications list: ");

        return message;
    }

    private DefaultListModel<App> getAppsModel() {
        appsModel = new DefaultListModel<>();

        List<App> tempList = appRepository.findAll();
        for (int i = 0; i != tempList.size(); ++i) {
            appsModel.add(i, tempList.get(i));
        }

        return appsModel;
    }

    private JList<App> getListOfApps() {
        listOfApps = new JList<>(getAppsModel());
        listOfApps.setLayoutOrientation(JList.VERTICAL);

        return listOfApps;
    }

    private JScrollPane getListScrollPane() {
        listScrollPane = new JScrollPane(getListOfApps());

        listScrollPane.setPreferredSize(new Dimension(230, 300));

        return listScrollPane;
    }

    private JButton getAddWebsite() {
        addApp = new JButton("+");

        addApp.addActionListener(e -> {
            addNewAppFrame.setVisible(true);
        });

        return addApp;
    }

    private JButton getRemoveWebsite() {
        removeApp = new JButton("-");

        removeApp.addActionListener(e -> {
            if (listOfApps.getSelectedIndex() >= 0) {
                App temp = appsModel.getElementAt(listOfApps.getSelectedIndex());
                appRepository.delete(temp);

                appsModel.remove(listOfApps.getSelectedIndex());
            }
        });

        return removeApp;
    }

}
