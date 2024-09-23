package com.thekuzea.diploma.gui.prompt.restriction;

import org.springframework.stereotype.Component;

import com.thekuzea.diploma.common.persistence.domain.app.App;
import com.thekuzea.diploma.common.persistence.domain.app.AppRepository;
import com.thekuzea.diploma.event.publisher.EventPublisher;

@Component
public class AddAppFrame extends AbstractAddRestrictionFrame<App> {

    public AddAppFrame(final EventPublisher eventPublisher, final AppRepository appRepository) {
        super(eventPublisher, appRepository);
    }

    @Override
    protected String getRestrictionTypeWindowName() {
        return "Add application";
    }

    @Override
    protected String getRestrictionTypeLabelName() {
        return "App:     ";
    }

    @Override
    protected App buildRestrictionEntity() {
        if (foreverBlockCheckbox.isSelected()) {
            return App.builder()
                    .name(restrictionTextInput.getText())
                    .isInfiniteRestriction(true)
                    .build();
        } else {
            return App.builder()
                    .name(restrictionTextInput.getText())
                    .isInfiniteRestriction(false)
                    .restrictionStartTime(parseTime(timeStartText.getText()))
                    .restrictionEndTime(parseTime(timeEndText.getText()))
                    .build();
        }
    }
}
