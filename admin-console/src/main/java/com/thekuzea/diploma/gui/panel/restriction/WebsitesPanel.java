package com.thekuzea.diploma.gui.panel.restriction;

import org.springframework.stereotype.Component;

import com.thekuzea.diploma.common.persistence.domain.website.Website;
import com.thekuzea.diploma.common.persistence.domain.website.WebsiteRepository;
import com.thekuzea.diploma.event.domain.restriction.model.CreateRestrictionFrameEvent;
import com.thekuzea.diploma.event.publisher.EventPublisher;

@Component
public class WebsitesPanel extends AbstractRestrictionPanel<Website> {

    public WebsitesPanel(final EventPublisher eventPublisher, final WebsiteRepository websiteRepository) {
        super(eventPublisher, websiteRepository);
    }

    @Override
    protected String getRestrictionZoneName() {
        return "Websites list: ";
    }

    @Override
    protected CreateRestrictionFrameEvent.RestrictionType getRestrictionType() {
        return CreateRestrictionFrameEvent.RestrictionType.WEBSITE;
    }
}
