package com.thekuzea.diploma.gui.panel;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.thekuzea.diploma.common.persistence.domain.user.User;

public abstract class AbstractInnerPanel<T> {

    private static final int PANEL_WIDTH = 300;

    private static final int PANEL_HEIGHT = 320;

    private static final int SCROLL_WIDTH = 250;

    private static final int SCROLL_HEIGHT = 300;

    protected final Logger log = LoggerFactory.getLogger(getLoggingChildClassName());

    protected DefaultListModel<T> model;

    protected abstract String getRestrictedItemsZoneName();

    protected abstract List<T> getListOfRestrictedItems(final User user);

    public void reinitializeModel(final User user) {
        if (model != null) {
            model.clear();
            populateModel(user);
        }
    }

    public JPanel createPanel(final User currentUser) {
        final JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        panel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        panel.add(createLabel());

        final JScrollPane scrollPane = new JScrollPane(createListOfItems(currentUser));
        scrollPane.setPreferredSize(new Dimension(SCROLL_WIDTH, SCROLL_HEIGHT));
        panel.add(scrollPane);

        return panel;
    }

    private JLabel createLabel() {
        final JLabel descriptionLabel = new JLabel();
        descriptionLabel.setText(getRestrictedItemsZoneName() + " list: ");

        return descriptionLabel;
    }

    private JList<T> createListOfItems(final User currentUser) {
        model = new DefaultListModel<>();

        populateModel(currentUser);

        final JList<T> listOfItems = new JList<>(model);
        listOfItems.setLayoutOrientation(JList.VERTICAL);

        return listOfItems;
    }

    private void populateModel(final User user) {
        final List<T> restrictedItems = getListOfRestrictedItems(user);
        if (CollectionUtils.isEmpty(restrictedItems)) {
            return;
        }

        for (int i = 0; i < restrictedItems.size(); i++) {
            model.add(i, restrictedItems.get(i));
        }

        if (log.isDebugEnabled()) {
            log.debug("Populating list model with restricted items: {}", restrictedItems);
        }
    }

    private Class<?> getLoggingChildClassName() {
        return this.getClass();
    }
}
