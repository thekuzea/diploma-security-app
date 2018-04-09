package com.thekuzea.diploma.gui.panel;

import com.thekuzea.diploma.gui.prompt.AddNewUser;
import com.thekuzea.diploma.gui.prompt.EditUser;
import com.thekuzea.diploma.model.User;
import com.thekuzea.diploma.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.List;

@Component
public class UsersPanel extends JPanel {

    private JLabel message;
    private JList<User> listOfUsers;
    private DefaultListModel<User> usersModel;
    private JScrollPane listScrollPane;

    private JButton addUser;
    private JButton removeUser;
    private JButton editUser;

    @Autowired
    private AddNewUser addNewUserFrame;

    @Autowired
    private EditUser editUserFrame;

    private WebsitesPanel websitesPanel;
    private AppsPanel appsPanel;

    private UserRepository userRepository;

    public UsersPanel(UserRepository userRepository, WebsitesPanel websitesPanel, AppsPanel appsPanel) {
        this.userRepository = userRepository;

        this.websitesPanel = websitesPanel;
        this.appsPanel = appsPanel;

        this.setLayout(new FlowLayout());
        this.setPreferredSize(new Dimension(250, 320));

        this.add(getMessage());
        this.add(getListScrollPane());
        this.add(getAddUser());
        this.add(getRemoveUser());
        this.add(getEditUser());

        this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "deselect");

        this.getActionMap().put("deselect", new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                try {
                    listOfUsers.clearSelection();
                } catch (NullPointerException ignored) { }

                websitesPanel.rearrangeList();
                appsPanel.rearrangeList();
            }
        });
    }

    public void addNewUser(User transmittableUser) {
        usersModel.add(usersModel.size(), transmittableUser);
    }

    private JLabel getMessage() {
        message = new JLabel("Users list: ");

        return message;
    }

    private DefaultListModel<User> getUsersModel() {
        usersModel = new DefaultListModel<>();

        List<User> tempList = userRepository.findAll();
        for (int i = 0; i != tempList.size(); ++i) {
            usersModel.add(i, tempList.get(i));
        }

        return usersModel;
    }

    private JList<User> getListOfUsers() {
        listOfUsers = new JList<>(getUsersModel());

        listOfUsers.setLayoutOrientation(JList.VERTICAL);
        listOfUsers.addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) {
                try {
                    websitesPanel.rearrangeList(listOfUsers.getSelectedValue().getForbiddenWebsites());
                    appsPanel.rearrangeList(listOfUsers.getSelectedValue().getForbiddenApps());
                } catch (NullPointerException ignored) { }
            }
        });

        return listOfUsers;
    }

    private JScrollPane getListScrollPane() {
        listScrollPane = new JScrollPane(getListOfUsers());

        listScrollPane.setPreferredSize(new Dimension(230, 300));

        return listScrollPane;
    }

    private JButton getAddUser() {
        addUser = new JButton("+");

        addUser.addActionListener(e -> {
            addNewUserFrame.setVisible(true);
        });

        return addUser;
    }

    private JButton getRemoveUser() {
        removeUser = new JButton("-");

        removeUser.addActionListener(e -> {
            if (listOfUsers.getSelectedIndex() >= 0) {
                User temp = usersModel.getElementAt(listOfUsers.getSelectedIndex());
                userRepository.delete(temp);

                usersModel.remove(listOfUsers.getSelectedIndex());
            }
        });

        return removeUser;
    }

    private JButton getEditUser() {
        editUser = new JButton("✎");

        editUser.addActionListener(e -> {
            if (listOfUsers.getSelectedIndex() >= 0) {
                User temp = usersModel.getElementAt(listOfUsers.getSelectedIndex());
                editUserFrame.transmitUser(temp);

                editUserFrame.setVisible(true);

                try {
                    listOfUsers.clearSelection();
                } catch (NullPointerException ignored) { }
                websitesPanel.rearrangeList();
                appsPanel.rearrangeList();
            }
        });

        return editUser;
    }

}
