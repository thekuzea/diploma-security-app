package com.thekuzea.diploma.restrict.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.thekuzea.diploma.common.persistence.domain.user.User;
import com.thekuzea.diploma.common.persistence.domain.user.UserRepository;
import com.thekuzea.diploma.config.async.AsyncConfig;
import com.thekuzea.diploma.restrict.AppRestrictingUtils;
import com.thekuzea.diploma.restrict.WebsiteRestrictingUtils;

import static com.thekuzea.diploma.UserApplication.USERNAME;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestrictionsScheduler {

    private static final int APPLY_RESTRICTIONS_DELAY_MILLIS = 5000;

    private final UserRepository userRepository;

    @Async(AsyncConfig.ASYNC_JOBS_THREAD_POOL_BEAN_BANE)
    @Scheduled(fixedDelay = APPLY_RESTRICTIONS_DELAY_MILLIS)
    public void configureRestrictions() {
        final User currentUser = userRepository.findByUsername(USERNAME);
        log.debug("About to update hosts file and query running processes for user: {}", currentUser.getUsername());

        WebsiteRestrictingUtils.updateHostsFileOnDemand(currentUser.getRestrictedWebsites());
        AppRestrictingUtils.queryProcessListAndKillOnDemand(currentUser.getRestrictedApps());
    }
}
