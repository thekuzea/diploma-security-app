package com.thekuzea.diploma.gui.prompt.restriction;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.time.LocalTime;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.thekuzea.diploma.event.domain.restriction.model.AddRestrictionEntityEvent;
import com.thekuzea.diploma.event.publisher.EventPublisher;

import static com.thekuzea.diploma.common.constant.GlobalConstants.EMPTY_STRING;
import static com.thekuzea.diploma.gui.constant.ActionButtons.SUBMIT;
import static com.thekuzea.diploma.gui.constant.ElementDimensions.TEXT_INPUT_COLUMNS_NUMBER;

@RequiredArgsConstructor
public abstract class AbstractAddRestrictionFrame<T> {

    private static final int FRAME_WIDTH = 300;

    private static final int FRAME_HEIGHT = 260;

    private static final int HOURS_TEXT_START_POSITION = 0;

    private static final int HOURS_TEXT_END_POSITION = 2;

    private static final int MINUTES_TEXT_START_POSITION = 3;

    private static final int MINUTES_TEXT_END_POSITION = 5;

    protected final EventPublisher eventPublisher;

    protected final MongoRepository<T, ?> restrictedEntityRepository;

    protected JFrame restrictionFrame;

    protected JTextField restrictionTextInput;

    protected JCheckBox foreverBlockCheckbox;

    protected JTextField timeStartText;

    protected JTextField timeEndText;

    public void createFrame() {
        final JFrame currentFrame = new JFrame();
        currentFrame.setTitle(getRestrictionTypeWindowName());
        currentFrame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        currentFrame.setLocationRelativeTo(null);
        currentFrame.add(createPanel());
        currentFrame.setVisible(true);

        restrictionFrame = currentFrame;
    }

    protected abstract String getRestrictionTypeWindowName();

    private JPanel createPanel() {
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        mainPanel.add(createRestrictionTypeLabel());
        mainPanel.add(createRestrictionTextInput());

        mainPanel.add(createForeverBlockLabel());
        mainPanel.add(createForeverBlockCheckbox());

        mainPanel.add(createTimeStartLabel());
        mainPanel.add(createTimeStartText());

        mainPanel.add(createTimeEndLabel());
        mainPanel.add(createTimeEndText());

        mainPanel.add(createSubmitButton());

        return mainPanel;
    }

    private JLabel createRestrictionTypeLabel() {
        return new JLabel(getRestrictionTypeLabelName());
    }

    protected abstract String getRestrictionTypeLabelName();

    private JTextField createRestrictionTextInput() {
        restrictionTextInput = new JTextField(TEXT_INPUT_COLUMNS_NUMBER);
        return restrictionTextInput;
    }

    private JLabel createForeverBlockLabel() {
        return new JLabel("Block forever?                               ");
    }

    private JCheckBox createForeverBlockCheckbox() {
        foreverBlockCheckbox = new JCheckBox();

        foreverBlockCheckbox.addActionListener(e -> {
            if (foreverBlockCheckbox.isSelected()) {
                timeStartText.setEditable(false);
                timeEndText.setEditable(false);

                timeStartText.setText(EMPTY_STRING);
                timeEndText.setText(EMPTY_STRING);
            } else {
                timeStartText.setEditable(true);
                timeEndText.setEditable(true);
            }
        });

        return foreverBlockCheckbox;
    }

    private JLabel createTimeStartLabel() {
        return new JLabel("Limit starts at: ");
    }

    private JTextField createTimeStartText() {
        timeStartText = new JTextField(TEXT_INPUT_COLUMNS_NUMBER);
        return timeStartText;
    }

    private JLabel createTimeEndLabel() {
        return new JLabel("Limit ends at: ");
    }

    private JTextField createTimeEndText() {
        timeEndText = new JTextField(TEXT_INPUT_COLUMNS_NUMBER);
        return timeEndText;
    }

    private JButton createSubmitButton() {
        final JButton submit = new JButton(SUBMIT);
        submit.addActionListener(this::doSubmitAction);

        return submit;
    }

    private void doSubmitAction(final ActionEvent e) {
        if (restrictionTextInput.getText().isEmpty()) {
            return;
        }

        try {
            final T entity = buildRestrictionEntity();
            restrictedEntityRepository.save(entity);

            closeWindow();

            final AddRestrictionEntityEvent<T> event = new AddRestrictionEntityEvent<>(entity);
            eventPublisher.sendEvent(event);
        } catch (IllegalArgumentException ignored) {
            timeStartText.setText(EMPTY_STRING);
            timeEndText.setText(EMPTY_STRING);
        }
    }

    protected abstract T buildRestrictionEntity();

    protected LocalTime parseTime(final String stringTime) throws IllegalArgumentException {
        final String hours = stringTime.substring(HOURS_TEXT_START_POSITION, HOURS_TEXT_END_POSITION);
        final String minutes = stringTime.substring(MINUTES_TEXT_START_POSITION, MINUTES_TEXT_END_POSITION);

        return LocalTime.of(Integer.parseInt(hours), Integer.parseInt(minutes));
    }

    private void closeWindow() {
        restrictionFrame.dispose();
    }
}
