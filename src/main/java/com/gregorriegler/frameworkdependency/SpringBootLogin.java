package com.gregorriegler.frameworkdependency;

import com.gregorriegler.frameworkdependency.model.Login;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;

import static java.util.Arrays.stream;

public class SpringBootLogin implements Login {

    private final Authentication authentication;

    public SpringBootLogin(Authentication authentication) {
        if (authentication == null) throw new AccessDeniedException("access denied!");
        this.authentication = authentication;
    }

    @Override
    public void assertAuthenticated() {
        if (!authentication.isAuthenticated()) {
            throw new AccessDeniedException("access denied!");
        }
    }

    @Override
    public void assertUserIsAdmin() {
        assertAuthenticated();
        if (!isAdmin()) {
            throw new AccessDeniedException("user not an admin");
        }
    }

    @Override
    public void assertHasAnyRole() {
        assertAuthenticated();
        if (!hasAnyRole("ROLE_ADMIN", "ROLE_USER")) {
            throw new AccessDeniedException("access denied!");
        }
    }

    private boolean isAdmin() {
        return hasAnyRole("ROLE_ADMIN");
    }

    private boolean hasAnyRole(String... roles) {
        return authentication.getAuthorities().stream()
            .anyMatch(granted -> stream(roles).anyMatch(expected -> expected.equals(granted.getAuthority())));
    }
}