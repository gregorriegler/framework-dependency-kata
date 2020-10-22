package com.gregorriegler.frameworkdependency;

import com.gregorriegler.frameworkdependency.model.Login;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SpringBootLogin implements Login {
    public SpringBootLogin() {
    }

    @Override
    public void assertUserIsAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin = authentication.getAuthorities().stream()
            .anyMatch(grantedAuthority -> "ROLE_ADMIN".equals(grantedAuthority.getAuthority()));
        if (!isAdmin) {
            throw new AccessDeniedException("user not an admin");
        }
    }
}