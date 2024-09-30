package com.thekuzea.diploma.gui.panel;

import java.util.List;

import com.thekuzea.diploma.common.persistence.domain.app.App;
import com.thekuzea.diploma.common.persistence.domain.user.User;

public class AppsInnerPanel extends AbstractInnerPanel<App> {

    @Override
    protected String getRestrictedItemsZoneName() {
        return "Applications";
    }

    @Override
    protected List<App> getListOfRestrictedItems(final User user) {
        return user.getRestrictedApps();
    }
}
