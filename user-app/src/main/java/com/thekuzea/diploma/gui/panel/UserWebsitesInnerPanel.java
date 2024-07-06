package com.thekuzea.diploma.gui.panel;

import java.util.List;

import com.thekuzea.diploma.common.persistence.domain.user.User;
import com.thekuzea.diploma.common.persistence.domain.website.Website;

public class UserWebsitesInnerPanel extends AbstractInnerPanel<Website> {

    public UserWebsitesInnerPanel(final User currentUser) {
        super(currentUser);
    }

    @Override
    protected String getRestrictedItemsZoneName() {
        return "Websites";
    }

    @Override
    protected List<Website> getListOfRestrictedItems() {
        return currentUser.getForbiddenWebsites();
    }
}
