package com.thekuzea.diploma.event.domain.user.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.thekuzea.diploma.event.domain.user.model.CreateUserFrameEvent;
import com.thekuzea.diploma.gui.prompt.user.AddUserFrame;
import com.thekuzea.diploma.gui.prompt.user.EditUserFrame;

@Component
@RequiredArgsConstructor
public class CreateUserFrameListener {

    private final AddUserFrame addUserFrame;

    private final EditUserFrame editUserFrame;

    @EventListener
    public void on(final CreateUserFrameEvent event) {
        switch (event.getAction()) {
            case ADD -> addUserFrame.createFrame();
            case EDIT -> editUserFrame.createFrame();
            default -> throw new UnsupportedOperationException("Unsupported type of action");
        }
    }
}
