package com.thekuzea.diploma.gui.panel.user;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Collections;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.thekuzea.diploma.common.persistence.domain.app.App;
import com.thekuzea.diploma.common.persistence.domain.user.User;
import com.thekuzea.diploma.common.persistence.domain.user.UserRepository;
import com.thekuzea.diploma.common.persistence.domain.website.Website;
import com.thekuzea.diploma.event.domain.restriction.model.RedrawAppListEvent;
import com.thekuzea.diploma.event.domain.restriction.model.RedrawDefaultAppListEvent;
import com.thekuzea.diploma.event.domain.restriction.model.RedrawDefaultWebsiteListEvent;
import com.thekuzea.diploma.event.domain.restriction.model.RedrawWebsiteListEvent;
import com.thekuzea.diploma.event.domain.user.model.CreateUserFrameEvent;
import com.thekuzea.diploma.event.domain.user.model.EditUserEntityEvent;
import com.thekuzea.diploma.event.publisher.EventPublisher;

import static com.thekuzea.diploma.gui.constant.ActionButtons.ADD;
import static com.thekuzea.diploma.gui.constant.ActionButtons.EDIT;
import static com.thekuzea.diploma.gui.constant.ActionButtons.REMOVE;

@Component
@RequiredArgsConstructor
public class UsersPanel {

    private static final int PANEL_WIDTH = 250;

    private static final int PANEL_HEIGHT = 320;

    private static final int SCROLL_WIDTH = 230;

    private static final int SCROLL_HEIGHT = 300;

    private final EventPublisher eventPublisher;

    private final UserRepository userRepository;

    private JList<User> listOfUsers;

    private DefaultListModel<User> usersModel;

    public JPanel createPanel() {
        final JPanel currentPanel = new JPanel();
        currentPanel.setLayout(new FlowLayout());
        currentPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        currentPanel.add(createPanelNameMessage());
        currentPanel.add(createListScrollPane());
        currentPanel.add(createAddUserButton());
        currentPanel.add(createRemoveUserButton());
        currentPanel.add(createEditUserButton());

        currentPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "deselect");

        currentPanel.getActionMap().put("deselect", new AbstractAction() {

            public void actionPerformed(ActionEvent e) {
                listOfUsers.clearSelection();

                final RedrawDefaultWebsiteListEvent websiteEvent = new RedrawDefaultWebsiteListEvent();
                eventPublisher.sendEvent(websiteEvent);

                final RedrawDefaultAppListEvent appEvent = new RedrawDefaultAppListEvent();
                eventPublisher.sendEvent(appEvent);
            }
        });

        return currentPanel;
    }

    public void addNewUser(User transmittableUser) {
        usersModel.add(usersModel.size(), transmittableUser);
    }

    private JLabel createPanelNameMessage() {
        return new JLabel("Users list: ");
    }

    private DefaultListModel<User> getUsersModel() {
        usersModel = new DefaultListModel<>();

        final List<User> userList = userRepository.findAll();
        for (int i = 0; i < userList.size(); i++) {
            usersModel.add(i, userList.get(i));
        }

        return usersModel;
    }

    private JList<User> getListOfUsers() {
        listOfUsers = new JList<>(getUsersModel());
        listOfUsers.setLayoutOrientation(JList.VERTICAL);

        listOfUsers.addListSelectionListener(event -> {
            if (listOfUsers.getSelectedValue() == null) {
                return;
            }

            final List<Website> forbiddenWebsites = listOfUsers.getSelectedValue().getForbiddenWebsites();
            final RedrawWebsiteListEvent websiteEvent = new RedrawWebsiteListEvent(forbiddenWebsites);
            eventPublisher.sendEvent(websiteEvent);

            final List<App> forbiddenApps = listOfUsers.getSelectedValue().getForbiddenApps();
            final RedrawAppListEvent appEvent = new RedrawAppListEvent(forbiddenApps);
            eventPublisher.sendEvent(appEvent);
        });

        return listOfUsers;
    }

    private JScrollPane createListScrollPane() {
        final JScrollPane listScrollPane = new JScrollPane(getListOfUsers());
        listScrollPane.setPreferredSize(new Dimension(SCROLL_WIDTH, SCROLL_HEIGHT));

        return listScrollPane;
    }

    private JButton createAddUserButton() {
        final JButton addUser = new JButton(ADD);

        addUser.addActionListener(e -> {
            final CreateUserFrameEvent event = new CreateUserFrameEvent(CreateUserFrameEvent.Action.ADD);
            eventPublisher.sendEvent(event);
        });

        return addUser;
    }

    private JButton createRemoveUserButton() {
        final JButton removeUser = new JButton(REMOVE);

        removeUser.addActionListener(e -> {
            if (listOfUsers.getSelectedIndex() >= 0) {
                final User selectedUser = usersModel.getElementAt(listOfUsers.getSelectedIndex());
                userRepository.delete(selectedUser);

                usersModel.remove(listOfUsers.getSelectedIndex());
            }
        });

        return removeUser;
    }

    private JButton createEditUserButton() {
        final JButton editUser = new JButton(EDIT);

        editUser.addActionListener(e -> {
            if (listOfUsers.getSelectedIndex() >= 0) {
                final CreateUserFrameEvent event = new CreateUserFrameEvent(CreateUserFrameEvent.Action.EDIT);
                eventPublisher.sendEvent(event);

                final User user = usersModel.getElementAt(listOfUsers.getSelectedIndex());
                final EditUserEntityEvent editUserEntityEvent = new EditUserEntityEvent(user);
                eventPublisher.sendEvent(editUserEntityEvent);

                listOfUsers.clearSelection();

                final RedrawWebsiteListEvent websiteEvent = new RedrawWebsiteListEvent(Collections.emptyList());
                eventPublisher.sendEvent(websiteEvent);

                final RedrawAppListEvent appEvent = new RedrawAppListEvent(Collections.emptyList());
                eventPublisher.sendEvent(appEvent);
            }
        });

        return editUser;
    }
}
