package com.thekuzea.diploma.event.domain.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.thekuzea.diploma.event.domain.DomainEvent;

@Getter
@AllArgsConstructor
public class CreateUserFrameEvent implements DomainEvent {

    private Action action;

    public enum Action {
        ADD,
        EDIT
    }
}
