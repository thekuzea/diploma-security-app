package com.thekuzea.diploma.event.domain.restriction.model;

import java.util.List;

import com.thekuzea.diploma.common.persistence.domain.app.App;

public class RedrawAppListEvent extends RedrawRestrictionListEvent<App> {

    public RedrawAppListEvent(final List<App> entityList) {
        super(entityList);
    }
}
