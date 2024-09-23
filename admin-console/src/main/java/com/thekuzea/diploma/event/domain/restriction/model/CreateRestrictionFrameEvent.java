package com.thekuzea.diploma.event.domain.restriction.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import com.thekuzea.diploma.event.domain.DomainEvent;

@Getter
@AllArgsConstructor
public class CreateRestrictionFrameEvent implements DomainEvent {

    private RestrictionType restrictionType;

    public enum RestrictionType {
        APP,
        WEBSITE
    }
}
