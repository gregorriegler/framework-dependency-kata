package com.gregorriegler.frameworkdependency.model;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class LibraryService {

    private final SecurityService securityService;
    private final LibraryRepository repository;

    public LibraryService(SecurityService securityService, LibraryRepository repository) {
        this.securityService = securityService;
        this.repository = repository;
    }

    public Collection<Book> getAllBooks() {
        if (securityService.isAuthenticated() && (securityService.isAdmin() || securityService.isUser())) {
            return repository.get();
        } else {
            throw new AccessDeniedException("only authenticated users are allowed to read books");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void add(Book book) {
        repository.add(book);
    }
}
