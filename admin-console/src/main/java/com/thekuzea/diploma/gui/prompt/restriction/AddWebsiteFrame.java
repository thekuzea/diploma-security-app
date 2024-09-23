package com.thekuzea.diploma.gui.prompt.restriction;

import org.springframework.stereotype.Component;

import com.thekuzea.diploma.common.persistence.domain.website.Website;
import com.thekuzea.diploma.common.persistence.domain.website.WebsiteRepository;
import com.thekuzea.diploma.event.publisher.EventPublisher;

@Component
public class AddWebsiteFrame extends AbstractAddRestrictionFrame<Website> {

    public AddWebsiteFrame(final EventPublisher eventPublisher, final WebsiteRepository websiteRepository) {
        super(eventPublisher, websiteRepository);
    }

    @Override
    protected String getRestrictionTypeWindowName() {
        return "Add website";
    }

    @Override
    protected String getRestrictionTypeLabelName() {
        return "Website: ";
    }

    @Override
    protected Website buildRestrictionEntity() {
        if (foreverBlockCheckbox.isSelected()) {
            return Website.builder()
                    .url(restrictionTextInput.getText())
                    .isInfiniteRestriction(true)
                    .build();
        } else {
            return Website.builder()
                    .url(restrictionTextInput.getText())
                    .isInfiniteRestriction(false)
                    .restrictionStartTime(parseTime(timeStartText.getText()))
                    .restrictionEndTime(parseTime(timeEndText.getText()))
                    .build();
        }
    }
}
