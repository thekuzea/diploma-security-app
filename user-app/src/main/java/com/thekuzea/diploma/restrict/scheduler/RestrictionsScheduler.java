package com.thekuzea.diploma.restrict.scheduler;

import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.thekuzea.diploma.common.persistence.domain.user.User;
import com.thekuzea.diploma.common.persistence.domain.user.UserRepository;
import com.thekuzea.diploma.config.async.AsyncConfig;
import com.thekuzea.diploma.helper.user.CurrentUserService;
import com.thekuzea.diploma.restrict.AppRestrictingUtils;
import com.thekuzea.diploma.restrict.WebsiteRestrictingUtils;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestrictionsScheduler {

    private final UserRepository userRepository;

    private final CurrentUserService currentUserService;

    @Async(AsyncConfig.ASYNC_JOBS_THREAD_POOL_BEAN_BANE)
    @Scheduled(fixedDelayString = "${local-wall.apply-restrictions-delay-seconds}", timeUnit = TimeUnit.SECONDS)
    public void configureRestrictions() {
        final String currentUsername = currentUserService.getCurrentUser();
        final User currentUser = userRepository.findByUsername(currentUsername);
        log.debug("About to update hosts file and query running processes for user: {}", currentUser.getUsername());

        WebsiteRestrictingUtils.updateHostsFileOnDemand(currentUser.getRestrictedWebsites());
        AppRestrictingUtils.queryProcessListAndKillOnDemand(currentUser.getRestrictedApps());
    }
}
