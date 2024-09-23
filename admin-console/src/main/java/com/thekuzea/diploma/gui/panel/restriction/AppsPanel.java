package com.thekuzea.diploma.gui.panel.restriction;

import org.springframework.stereotype.Component;

import com.thekuzea.diploma.common.persistence.domain.app.App;
import com.thekuzea.diploma.common.persistence.domain.app.AppRepository;
import com.thekuzea.diploma.event.domain.restriction.model.CreateRestrictionFrameEvent;
import com.thekuzea.diploma.event.publisher.EventPublisher;

@Component
public class AppsPanel extends AbstractRestrictionPanel<App> {

    public AppsPanel(final EventPublisher eventPublisher, final AppRepository appRepository) {
        super(eventPublisher, appRepository);
    }

    @Override
    protected String getRestrictionZoneName() {
        return "Applications list: ";
    }

    @Override
    protected CreateRestrictionFrameEvent.RestrictionType getRestrictionType() {
        return CreateRestrictionFrameEvent.RestrictionType.APP;
    }
}
