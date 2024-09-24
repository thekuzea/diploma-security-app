package com.thekuzea.diploma.event.domain.user.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.thekuzea.diploma.common.persistence.domain.user.User;
import com.thekuzea.diploma.event.domain.DomainEvent;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntityActionEvent implements DomainEvent {

    private User user;
}
