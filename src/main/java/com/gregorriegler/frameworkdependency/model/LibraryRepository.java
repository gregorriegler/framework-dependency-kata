package com.gregorriegler.frameworkdependency.model;

import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class LibraryRepository {
    private final Map<String, Book> books = new HashMap<>();

    public Collection<Book> get() {
        return books.values();
    }

    public void add(Book book) {
        books.put(book.isbn, book);
    }

    public void clear() {
        books.clear();
    }
}
