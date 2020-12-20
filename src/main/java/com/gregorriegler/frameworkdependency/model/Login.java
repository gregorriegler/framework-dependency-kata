package com.gregorriegler.frameworkdependency.model;

public interface Login {
    void assertAuthenticated();

    void assertUserIsAdmin();

    void assertHasAnyRole();
}
