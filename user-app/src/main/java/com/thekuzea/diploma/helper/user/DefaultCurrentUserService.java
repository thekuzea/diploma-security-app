package com.thekuzea.diploma.helper.user;

import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class DefaultCurrentUserService implements CurrentUserService {

    @Override
    public String getCurrentUser() {
        final String user = System.getenv("USER");
        final String username = System.getenv("USERNAME");

        return Optional.ofNullable(user)
                .or(() -> Optional.ofNullable(username))
                .orElseThrow(() -> new IllegalStateException("Unable to determine current user on machine"));
    }
}
