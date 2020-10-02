package com.gregorriegler.frameworkdependency.model;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class LibraryService {

    private final LibraryRepository repository;

    public LibraryService(LibraryRepository repository) {
        this.repository = repository;
    }

    public Collection<Book> getAllBooks() {
        return repository.get();
    }

    @PreAuthorize("hasRole('MANAGER')")
    public void add(Book book) {
        repository.add(book);
    }
}
