package com.thekuzea.diploma.event.domain.restriction.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.ResolvableType;
import org.springframework.core.ResolvableTypeProvider;

import com.thekuzea.diploma.event.domain.DomainEvent;

@Getter
@AllArgsConstructor
public class AddRestrictionEntityEvent<T> implements DomainEvent, ResolvableTypeProvider {

    private T entity;

    @Override
    public ResolvableType getResolvableType() {
        return ResolvableType.forClassWithGenerics(getClass(), ResolvableType.forInstance(this.entity));
    }
}
