package com.thekuzea.diploma.gui.panel;

import java.util.List;

import com.thekuzea.diploma.common.persistence.domain.app.App;
import com.thekuzea.diploma.common.persistence.domain.user.User;

public class AppsInnerPanel extends AbstractInnerPanel<App> {

    public AppsInnerPanel(final User currentUser) {
        super(currentUser);
    }

    @Override
    protected String getRestrictedItemsZoneName() {
        return "Applications";
    }

    @Override
    protected List<App> getListOfRestrictedItems() {
        return currentUser.getRestrictedApps();
    }
}
