package com.thekuzea.diploma.gui.prompt;

import com.thekuzea.diploma.gui.panel.UsersPanel;
import com.thekuzea.diploma.common.persistence.domain.user.User;
import com.thekuzea.diploma.common.persistence.domain.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;

@Component
public class AddNewUser extends JFrame {

    private JPanel mainPanel;

    private JLabel usernameLabel;
    private JTextField usernameText;

    private JLabel roleLabel;
    private JTextField roleText;

    private JButton submit;

    private User transmittableUser;

    @Autowired
    private UsersPanel usersPanel;

    @Autowired
    private UserRepository userRepository;

    public AddNewUser() {
        this.setTitle("Add user");
        this.setSize(280, 160);
        this.setLocationRelativeTo(null);
        this.add(getElements());
    }

    private JPanel getElements() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        usernameLabel = new JLabel("Username: ");
        usernameText = new JTextField(20);

        roleLabel = new JLabel("Role: ");
        roleText = new JTextField(20);

        submit = new JButton("Submit");
        submit.addActionListener(e -> {
            if(usernameText.getText().length() > 0) {
                transmittableUser = User.builder()
                        .username(usernameText.getText())
                        .role(roleText.getText())
                        .build();
                userRepository.save(transmittableUser);

                usersPanel.addNewUser(transmittableUser);

                destroyWindow();
            }
        });

        mainPanel.add(usernameLabel);
        mainPanel.add(usernameText);
        mainPanel.add(roleLabel);
        mainPanel.add(roleText);
        mainPanel.add(submit);

        return mainPanel;
    }

    private void destroyWindow() {
        usernameText.setText("");
        roleText.setText("");

        this.setVisible(false);
    }
}
