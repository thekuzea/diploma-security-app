package com.thekuzea.diploma.gui.prompt.user;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.thekuzea.diploma.common.persistence.domain.user.User;
import com.thekuzea.diploma.common.persistence.domain.user.UserRepository;
import com.thekuzea.diploma.gui.panel.user.UsersPanel;

import static com.thekuzea.diploma.common.constant.GlobalConstants.EMPTY_STRING;
import static com.thekuzea.diploma.gui.constant.ActionButtons.SUBMIT;

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

        submit = new JButton(SUBMIT);
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
        usernameText.setText(EMPTY_STRING);
        roleText.setText(EMPTY_STRING);

        this.setVisible(false);
    }
}
