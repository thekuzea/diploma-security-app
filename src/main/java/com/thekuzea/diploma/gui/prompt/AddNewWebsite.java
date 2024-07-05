package com.thekuzea.diploma.gui.prompt;

import com.thekuzea.diploma.gui.panel.WebsitesPanel;
import com.thekuzea.diploma.model.Website;
import com.thekuzea.diploma.repository.WebsiteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;

@Component
public class AddNewWebsite extends JFrame {

    private JPanel mainPanel;

    private JLabel websiteLabel;
    private JTextField websiteText;

    private JLabel foreverBlockLabel;
    private JCheckBox foreverBlockCheckbox;

    private JLabel timeStartLabel;
    private JTextField timeStartText;

    private JLabel timeEndLabel;
    private JTextField timeEndText;

    private JButton submit;

    private Website transmittableWebsite;

    @Autowired
    private WebsitesPanel websitesPanel;

    @Autowired
    private WebsiteRepository websiteRepository;

    public AddNewWebsite() {
        this.setTitle("Add website");
        this.setSize(300, 260);
        this.setLocationRelativeTo(null);
        this.add(getMainPanel());
    }

    private JPanel getMainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new FlowLayout());

        mainPanel.add(getWebsiteLabel());
        mainPanel.add(getWebsiteText());

        mainPanel.add(getForeverBlockLabel());
        mainPanel.add(getForeverBlockCheckbox());

        mainPanel.add(getTimeStartLabel());
        mainPanel.add(getTimeStartText());

        mainPanel.add(getTimeEndLabel());
        mainPanel.add(getTimeEndText());

        mainPanel.add(getSubmit());

        return mainPanel;
    }

    private JLabel getWebsiteLabel() {
        websiteLabel = new JLabel("Website: ");
        return websiteLabel;
    }

    private JTextField getWebsiteText() {
        websiteText = new JTextField(20);
        return websiteText;
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
            if(websiteText.getText().length() > 0) {

                if(foreverBlockCheckbox.isSelected()) {
                    transmittableWebsite = Website.builder()
                            .url(websiteText.getText())
                            .isInfiniteRestriction(true)
                            .build();
                } else {
                    try {
                        transmittableWebsite = Website.builder()
                                .url(websiteText.getText())
                                .isInfiniteRestriction(false)
                                .restrictionStartTime(parseTime(timeStartText.getText()))
                                .restrictionEndTime(parseTime(timeEndText.getText()))
                                .build();
                    } catch (IllegalArgumentException | StringIndexOutOfBoundsException ignored) {
                        return;
                    }
                }

                websiteRepository.save(transmittableWebsite);

                destroyWindow();

                websitesPanel.addNewWebsite(transmittableWebsite);
            }
        });

        return submit;
    }

    private LocalTime parseTime(String stringTime) {
        LocalTime tempTime;

        try {
            String hours = stringTime.substring(0,2);
            String minutes = stringTime.substring(3,5);

            tempTime = LocalTime.of(Integer.parseInt(hours), Integer.parseInt(minutes));

            return tempTime;
        } catch (IllegalArgumentException ignored) {
            throw new IllegalArgumentException();
        } catch (StringIndexOutOfBoundsException ignored) {
            throw new StringIndexOutOfBoundsException();
        }
    }

    private void destroyWindow() {
        websiteText.setText("");
        foreverBlockCheckbox.setSelected(false);

        timeStartText.setText("");
        timeStartText.setEditable(true);

        timeEndText.setText("");
        timeEndText.setEditable(true);

        this.setVisible(false);
    }
}