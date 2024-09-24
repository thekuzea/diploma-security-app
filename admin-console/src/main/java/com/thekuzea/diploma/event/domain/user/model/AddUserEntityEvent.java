package com.thekuzea.diploma.event.domain.user.model;

import com.thekuzea.diploma.common.persistence.domain.user.User;

public class AddUserEntityEvent extends UserEntityActionEvent {

    public AddUserEntityEvent(final User user) {
        super(user);
    }
}
