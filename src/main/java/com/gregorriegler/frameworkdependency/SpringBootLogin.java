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
    public void assertIsAdmin() {
        assertHasRole("ROLE_ADMIN");
    }

    @Override
    public void assertIsAdminOrUser() {
        assertHasRole("ROLE_ADMIN", "ROLE_USER");
    }

    public void assertHasRole(String... roles) {
        if (!hasAnyRole(roles)) {
            throw new AccessDeniedException("access denied!");
        }
    }

    private boolean hasAnyRole(String... roles) {
        return authentication.getAuthorities().stream()
            .anyMatch(granted -> stream(roles).anyMatch(expected -> expected.equals(granted.getAuthority())));
    }
}