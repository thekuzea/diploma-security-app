package com.thekuzea.diploma.event.domain.restriction.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.thekuzea.diploma.event.domain.restriction.model.CreateRestrictionFrameEvent;
import com.thekuzea.diploma.gui.prompt.restriction.AddAppFrame;
import com.thekuzea.diploma.gui.prompt.restriction.AddWebsiteFrame;

@Component
@RequiredArgsConstructor
public class CreateRestrictionFrameListener {

    private final AddAppFrame addAppFrame;

    private final AddWebsiteFrame addWebsiteFrame;

    @EventListener
    public void on(final CreateRestrictionFrameEvent event) {
        switch (event.getRestrictionType()) {
            case APP -> addAppFrame.createFrame();
            case WEBSITE -> addWebsiteFrame.createFrame();
            default -> throw new UnsupportedOperationException("Unsupported type of restriction");
        }
    }
}
