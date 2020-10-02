package com.gregorriegler.frameworkdependency.model;

import com.gregorriegler.frameworkdependency.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class LibraryService {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private BookRepository repository;

    public Collection<Book> getAllBooks() {
        if (securityService.isAuthenticated() && (securityService.isAdmin() || securityService.isUser())) {
            return repository.findAll();
        } else {
            throw new AccessDeniedException("access denied!");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    public void add(Book book) {
        repository.save(book);
    }
}
