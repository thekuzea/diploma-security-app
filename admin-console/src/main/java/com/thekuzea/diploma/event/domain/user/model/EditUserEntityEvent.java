package com.thekuzea.diploma.event.domain.user.model;

import com.thekuzea.diploma.common.persistence.domain.user.User;

public class EditUserEntityEvent extends UserEntityActionEvent {

    public EditUserEntityEvent(final User user) {
        super(user);
    }
}
