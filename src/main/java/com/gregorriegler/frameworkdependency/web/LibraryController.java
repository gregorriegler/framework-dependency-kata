package com.gregorriegler.frameworkdependency.web;

import com.gregorriegler.frameworkdependency.model.Book;
import com.gregorriegler.frameworkdependency.model.LibraryService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class LibraryController {

    private final LibraryService libraryService;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @GetMapping("/library")
    public Collection<Book> get() {
        return libraryService.getAllBooks();
    }

    @PutMapping("/library/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void add(@PathVariable String isbn, @RequestBody CreateBookRequest request) {
        libraryService.add(new Book(isbn, request.title, request.author));
    }
}
