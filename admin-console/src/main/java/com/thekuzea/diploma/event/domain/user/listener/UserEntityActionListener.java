package com.thekuzea.diploma.event.domain.user.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.thekuzea.diploma.common.persistence.domain.user.User;
import com.thekuzea.diploma.event.domain.user.model.AddUserEntityEvent;
import com.thekuzea.diploma.event.domain.user.model.EditUserEntityEvent;
import com.thekuzea.diploma.gui.panel.user.UsersPanel;
import com.thekuzea.diploma.gui.prompt.user.EditUserFrame;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEntityActionListener {

    private final UsersPanel usersPanel;

    private final EditUserFrame editUserFrame;

    @EventListener
    public void onAddUserEntityEvent(final AddUserEntityEvent event) {
        final User user = event.getUser();

        if (log.isDebugEnabled()) {
            log.debug("About to add new user: [{}] into panel", user);
        }

        usersPanel.addNewUser(user);
    }

    @EventListener
    public void onEditUserEntityEvent(final EditUserEntityEvent event) {
        final User user = event.getUser();

        if (log.isDebugEnabled()) {
            log.debug("About to set user: [{}] and prepare edit user window", user);
        }

        editUserFrame.setUserAndPrepareWindow(user);
    }
}
