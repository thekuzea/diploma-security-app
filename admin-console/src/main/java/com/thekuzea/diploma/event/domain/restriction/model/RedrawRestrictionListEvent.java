package com.thekuzea.diploma.event.domain.restriction.model;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import com.thekuzea.diploma.event.domain.DomainEvent;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class RedrawRestrictionListEvent<T> implements DomainEvent {

    private List<T> entityList;
}
