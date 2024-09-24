package com.thekuzea.diploma.event.domain.restriction.listener;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.thekuzea.diploma.common.persistence.domain.app.App;
import com.thekuzea.diploma.common.persistence.domain.website.Website;
import com.thekuzea.diploma.event.domain.restriction.model.RedrawAppListEvent;
import com.thekuzea.diploma.event.domain.restriction.model.RedrawWebsiteListEvent;
import com.thekuzea.diploma.gui.panel.restriction.AppsPanel;
import com.thekuzea.diploma.gui.panel.restriction.WebsitesPanel;

@Component
@RequiredArgsConstructor
public class RedrawRestrictionListListener {

    private final AppsPanel appsPanel;

    private final WebsitesPanel websitesPanel;

    @EventListener
    public void onRedrawRestrictionAppList(final RedrawAppListEvent event) {
        final List<App> appList = event.getEntityList();
        if (CollectionUtils.isEmpty(appList)) {
            appsPanel.redrawList();
        } else {
            appsPanel.redrawListFrom(appList);
        }
    }

    @EventListener
    public void onRedrawRestrictionWebsiteList(final RedrawWebsiteListEvent event) {
        final List<Website> websiteList = event.getEntityList();
        if (CollectionUtils.isEmpty(websiteList)) {
            websitesPanel.redrawList();
        } else {
            websitesPanel.redrawListFrom(websiteList);
        }
    }
}
