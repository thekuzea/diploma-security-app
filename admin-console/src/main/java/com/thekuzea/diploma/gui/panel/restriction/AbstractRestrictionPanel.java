package com.thekuzea.diploma.gui.panel.restriction;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.thekuzea.diploma.event.domain.restriction.model.CreateRestrictionFrameEvent;
import com.thekuzea.diploma.event.publisher.EventPublisher;

import static com.thekuzea.diploma.gui.constant.ActionButtons.ADD;
import static com.thekuzea.diploma.gui.constant.ActionButtons.REMOVE;

@RequiredArgsConstructor
public abstract class AbstractRestrictionPanel<T> {

    private static final int PANEL_WIDTH = 250;

    private static final int PANEL_HEIGHT = 320;

    private static final int SCROLL_WIDTH = 230;

    private static final int SCROLL_HEIGHT = 300;

    protected final EventPublisher eventPublisher;

    protected final MongoRepository<T, ?> restrictedEntityRepository;

    protected JList<T> listOfRestrictions;

    protected DefaultListModel<T> restrictionsListModel;

    public JPanel createPanel() {
        final JPanel currentPanel = new JPanel();
        currentPanel.setLayout(new FlowLayout());
        currentPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        currentPanel.add(createZoneLabel());
        currentPanel.add(createListScrollPane());
        currentPanel.add(createAddRestrictionButton());
        currentPanel.add(createRemoveRestrictionButton());

        return currentPanel;
    }

    public void addNewRestriction(final T restriction) {
        restrictionsListModel.add(restrictionsListModel.size(), restriction);
    }

    public void rearrangeList() {
        restrictionsListModel.clear();

        final List<T> list = restrictedEntityRepository.findAll();
        for (int i = 0; i < list.size(); i++) {
            restrictionsListModel.add(i, list.get(i));
        }
    }

    public void rearrangeList(final List<T> list) {
        restrictionsListModel.clear();

        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                restrictionsListModel.add(i, list.get(i));
            }
        }
    }

    protected abstract String getRestrictionZoneName();

    private JLabel createZoneLabel() {
        return new JLabel(getRestrictionZoneName());
    }

    private DefaultListModel<T> createRestrictionsListModel() {
        restrictionsListModel = new DefaultListModel<>();

        final List<T> restrictedEntityList = restrictedEntityRepository.findAll();
        for (int i = 0; i < restrictedEntityList.size(); i++) {
            restrictionsListModel.add(i, restrictedEntityList.get(i));
        }

        return restrictionsListModel;
    }

    private JList<T> createListOfRestrictions() {
        listOfRestrictions = new JList<>(createRestrictionsListModel());
        listOfRestrictions.setLayoutOrientation(JList.VERTICAL);

        return listOfRestrictions;
    }

    private JScrollPane createListScrollPane() {
        final JScrollPane listScrollPane = new JScrollPane(createListOfRestrictions());
        listScrollPane.setPreferredSize(new Dimension(SCROLL_WIDTH, SCROLL_HEIGHT));

        return listScrollPane;
    }

    protected abstract CreateRestrictionFrameEvent.RestrictionType getRestrictionType();

    private JButton createAddRestrictionButton() {
        final JButton addRestriction = new JButton(ADD);
        addRestriction.addActionListener(e -> {
            final CreateRestrictionFrameEvent event = new CreateRestrictionFrameEvent(getRestrictionType());
            eventPublisher.sendEvent(event);
        });

        return addRestriction;
    }

    private JButton createRemoveRestrictionButton() {
        final JButton removeRestriction = new JButton(REMOVE);

        removeRestriction.addActionListener(e -> {
            if (listOfRestrictions.getSelectedIndex() >= 0) {
                final T foundElement = restrictionsListModel.getElementAt(listOfRestrictions.getSelectedIndex());
                restrictedEntityRepository.delete(foundElement);

                restrictionsListModel.remove(listOfRestrictions.getSelectedIndex());
            }
        });

        return removeRestriction;
    }
}
