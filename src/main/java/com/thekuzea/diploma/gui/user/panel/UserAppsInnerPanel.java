package com.thekuzea.diploma.gui.user.panel;

import java.util.List;

import com.thekuzea.diploma.model.App;
import com.thekuzea.diploma.model.User;

public class UserAppsInnerPanel extends AbstractInnerPanel<App> {

    public UserAppsInnerPanel(final User currentUser) {
        super(currentUser);
    }

    @Override
    protected String getRestrictedItemsZoneName() {
        return "Applications";
    }

    @Override
    protected List<App> getListOfRestrictedItems() {
        return currentUser.getForbiddenApps();
    }
}
