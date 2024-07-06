package com.thekuzea.diploma.gui.panel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MainPanel {

    private static final int MAIN_WINDOW_DESCRIPTION_WIDTH = 200;

    private static final int MAIN_WINDOW_DESCRIPTION_HEIGHT = 40;

    public JPanel createMainPanel(final JPanel websitesPanel, final JPanel appsPanel) {
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(createMainLabel(), BorderLayout.NORTH);
        mainPanel.add(websitesPanel, BorderLayout.WEST);
        mainPanel.add(appsPanel, BorderLayout.EAST);

        return mainPanel;
    }

    private JLabel createMainLabel() {
        final JLabel mainLabel = new JLabel();
        mainLabel.setText("Lists of blocked services");
        mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
        mainLabel.setPreferredSize(new Dimension(MAIN_WINDOW_DESCRIPTION_WIDTH, MAIN_WINDOW_DESCRIPTION_HEIGHT));

        return mainLabel;
    }
}
