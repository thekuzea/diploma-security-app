package com.thekuzea.diploma.gui.user.panel;

import java.util.List;

import com.thekuzea.diploma.model.User;
import com.thekuzea.diploma.model.Website;

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
