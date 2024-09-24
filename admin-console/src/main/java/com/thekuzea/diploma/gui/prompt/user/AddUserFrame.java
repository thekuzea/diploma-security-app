package com.thekuzea.diploma.gui.prompt.user;

import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import com.thekuzea.diploma.common.persistence.domain.user.User;
import com.thekuzea.diploma.common.persistence.domain.user.UserRepository;
import com.thekuzea.diploma.event.domain.user.model.AddUserEntityEvent;
import com.thekuzea.diploma.event.publisher.EventPublisher;

import static com.thekuzea.diploma.gui.constant.ActionButtons.SUBMIT;
import static com.thekuzea.diploma.gui.constant.ElementDimensions.TEXT_INPUT_COLUMNS_NUMBER;

@Component
@RequiredArgsConstructor
public class AddUserFrame {

    private static final int FRAME_WIDTH = 280;

    private static final int FRAME_HEIGHT = 160;

    private final EventPublisher eventPublisher;

    private final UserRepository userRepository;

    private JFrame frame;

    private JTextField usernameText;

    private JTextField roleText;

    public void createFrame() {
        final JFrame currentFrame = new JFrame();
        currentFrame.setTitle("Add user");
        currentFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        currentFrame.setLocationRelativeTo(null);
        currentFrame.add(createPanel());
        currentFrame.setVisible(true);

        frame = currentFrame;
    }

    private JPanel createPanel() {
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        mainPanel.add(createUsernameLabel());
        mainPanel.add(createUsernameInputField());

        mainPanel.add(createRoleLabel());
        mainPanel.add(createRoleInputField());

        mainPanel.add(createAddUserSubmitButton());

        return mainPanel;
    }

    private JLabel createUsernameLabel() {
        return new JLabel("Username: ");
    }

    private JTextField createUsernameInputField() {
        usernameText = new JTextField(TEXT_INPUT_COLUMNS_NUMBER);
        return usernameText;
    }

    private JLabel createRoleLabel() {
        return new JLabel("Role: ");
    }

    private JTextField createRoleInputField() {
        roleText = new JTextField(TEXT_INPUT_COLUMNS_NUMBER);
        return roleText;
    }

    private JButton createAddUserSubmitButton() {
        final JButton submit = new JButton(SUBMIT);

        submit.addActionListener(e -> {
            if(!usernameText.getText().isEmpty()) {
                final User user = User.builder()
                        .username(usernameText.getText())
                        .role(roleText.getText())
                        .build();

                userRepository.save(user);

                final AddUserEntityEvent event = new AddUserEntityEvent(user);
                eventPublisher.sendEvent(event);

                destroyWindow();
            }
        });

        return submit;
    }

    private void destroyWindow() {
        frame.dispose();
    }
}
