package com.thekuzea.diploma.helper.user;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Primary
@Component
@Profile("dev")
public class DevModeCurrentUserService implements CurrentUserService {

    @Value("${local-wall.current-user}")
    private String currentUser;

    @Override
    public String getCurrentUser() {
        return currentUser;
    }
}
