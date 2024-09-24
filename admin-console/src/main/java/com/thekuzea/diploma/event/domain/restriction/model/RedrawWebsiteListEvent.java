package com.thekuzea.diploma.event.domain.restriction.model;

import java.util.List;

import com.thekuzea.diploma.common.persistence.domain.website.Website;

public class RedrawWebsiteListEvent extends RedrawRestrictionListEvent<Website> {

    public RedrawWebsiteListEvent(final List<Website> entityList) {
        super(entityList);
    }
}
