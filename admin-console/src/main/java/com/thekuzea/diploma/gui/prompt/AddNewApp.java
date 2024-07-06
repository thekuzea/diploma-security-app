package com.thekuzea.diploma.gui.prompt;

import com.thekuzea.diploma.gui.panel.AppsPanel;
import com.thekuzea.diploma.common.persistence.domain.app.App;
import com.thekuzea.diploma.common.persistence.domain.app.AppRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;

@Component
public class AddNewApp extends JFrame {

    private JPanel mainPanel;

    private JLabel appLabel;
    private JTextField appText;

    private JLabel foreverBlockLabel;
    private JCheckBox foreverBlockCheckbox;

    private JLabel timeStartLabel;
    private JTextField timeStartText;

    private JLabel timeEndLabel;
    private JTextField timeEndText;

    private JButton submit;

    private App transmittableApp;

    @Autowired
    private AppsPanel appsPanel;

    @Autowired
    private AppRepository appRepository;

    public AddNewApp() {
        this.setTitle("Add application");
        this.setSize(300, 260);
        this.setLocationRelativeTo(null);
        this.add(getMainPanel());
    }

    private JPanel getMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        mainPanel.add(getAppLabel());
        mainPanel.add(getAppText());

        mainPanel.add(getForeverBlockLabel());
        mainPanel.add(getForeverBlockCheckbox());

        mainPanel.add(getTimeStartLabel());
        mainPanel.add(getTimeStartText());

        mainPanel.add(getTimeEndLabel());
        mainPanel.add(getTimeEndText());

        mainPanel.add(getSubmit());

        return mainPanel;
    }

    private JLabel getAppLabel() {
        appLabel = new JLabel("App:     ");
        return appLabel;
    }

    private JTextField getAppText() {
        appText = new JTextField(20);
        return appText;
    }

    private JLabel getForeverBlockLabel() {
        foreverBlockLabel = new JLabel("Block forever?                               ");
        return foreverBlockLabel;
    }

    private JCheckBox getForeverBlockCheckbox() {
        foreverBlockCheckbox = new JCheckBox();

        foreverBlockCheckbox.addActionListener(e -> {
            if(foreverBlockCheckbox.isSelected()) {
                timeStartText.setEditable(false);
                timeEndText.setEditable(false);

                timeStartText.setText("");
                timeEndText.setText("");
            } else {
                timeStartText.setEditable(true);
                timeEndText.setEditable(true);
            }
        });

        return foreverBlockCheckbox;
    }

    private JLabel getTimeStartLabel() {
        timeStartLabel = new JLabel("Limit starts: ");
        return timeStartLabel;
    }

    private JTextField getTimeStartText() {
        timeStartText = new JTextField(20);
        return timeStartText;
    }

    private JLabel getTimeEndLabel() {
        timeEndLabel = new JLabel("Limit ends: ");
        return timeEndLabel;
    }

    private JTextField getTimeEndText() {
        timeEndText = new JTextField(20);
        return timeEndText;
    }

    private JButton getSubmit() {
        submit = new JButton("Submit");

        submit.addActionListener(e -> {
            if(appText.getText().length() > 0) {

                if (foreverBlockCheckbox.isSelected()) {
                    transmittableApp = App.builder()
                            .name(appText.getText())
                            .isInfiniteRestriction(true)
                            .build();
                } else {
                    try {
                        transmittableApp = App.builder()
                                .name(appText.getText())
                                .isInfiniteRestriction(false)
                                .restrictionStartTime(parseTime(timeStartText.getText()))
                                .restrictionEndTime(parseTime(timeEndText.getText()))
                                .build();
                    } catch (IllegalArgumentException ignored) {
                        timeStartText.setText("");
                        timeEndText.setText("");
                        return;
                    }
                }

                appRepository.save(transmittableApp);

                destroyWindow();

                appsPanel.addNewApp(transmittableApp);
            }
        });

        return submit;
    }

    private LocalTime parseTime(String stringTime) throws IllegalArgumentException {
        LocalTime tempTime;

        try {
            String hours = stringTime.substring(0,2);
            String minutes = stringTime.substring(3,5);

            tempTime = LocalTime.of(Integer.parseInt(hours), Integer.parseInt(minutes));

            return tempTime;
        } catch (IllegalArgumentException ignored) {
            throw new IllegalArgumentException();
        }
    }

    private void destroyWindow() {
        appText.setText("");
        foreverBlockCheckbox.setSelected(false);

        timeStartText.setText("");
        timeStartText.setEditable(true);

        timeEndText.setText("");
        timeEndText.setEditable(true);

        this.setVisible(false);
    }
}
