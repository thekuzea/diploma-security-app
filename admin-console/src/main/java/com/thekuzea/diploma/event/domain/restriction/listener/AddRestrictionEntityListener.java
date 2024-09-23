package com.thekuzea.diploma.event.domain.restriction.listener;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.thekuzea.diploma.common.persistence.domain.app.App;
import com.thekuzea.diploma.common.persistence.domain.website.Website;
import com.thekuzea.diploma.event.domain.restriction.model.AddRestrictionEntityEvent;
import com.thekuzea.diploma.gui.panel.restriction.AppsPanel;
import com.thekuzea.diploma.gui.panel.restriction.WebsitesPanel;

@Component
@RequiredArgsConstructor
public class AddRestrictionEntityListener {

    private final AppsPanel appsPanel;

    private final WebsitesPanel websitesPanel;

    @EventListener
    public void onAppRestrictionEvent(final AddRestrictionEntityEvent<App> appRestrictionEvent) {
        final App app = appRestrictionEvent.getEntity();
        appsPanel.addNewRestriction(app);
    }

    @EventListener
    public void onWebsiteRestrictionEvent(final AddRestrictionEntityEvent<Website> websiteRestrictionEvent) {
        final Website website = websiteRestrictionEvent.getEntity();
        websitesPanel.addNewRestriction(website);
    }
}
