package com.thekuzea.diploma.event.domain.restriction.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.thekuzea.diploma.event.domain.restriction.model.CreateRestrictionFrameEvent;
import com.thekuzea.diploma.gui.prompt.restriction.AddAppFrame;
import com.thekuzea.diploma.gui.prompt.restriction.AddWebsiteFrame;

@Slf4j
@Component
@RequiredArgsConstructor
public class CreateRestrictionFrameListener {

    private final AddAppFrame addAppFrame;

    private final AddWebsiteFrame addWebsiteFrame;

    @EventListener
    public void on(final CreateRestrictionFrameEvent event) {
        final CreateRestrictionFrameEvent.RestrictionType restrictionType = event.getRestrictionType();

        log.debug("About to create {} restriction frame", restrictionType);
        switch (restrictionType) {
            case APP -> addAppFrame.createFrame();
            case WEBSITE -> addWebsiteFrame.createFrame();
            default -> throw new UnsupportedOperationException("Unsupported type of restriction");
        }
    }
}
