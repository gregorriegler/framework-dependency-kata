package com.gregorriegler.frameworkdependency.web;

import com.gregorriegler.frameworkdependency.model.Book;
import com.gregorriegler.frameworkdependency.model.LibraryService;
import com.gregorriegler.frameworkdependency.SpringBootLogin;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/books")
    public ResponseEntity<Collection<Book>> get() {
        return libraryService.getAllBooks();
    }

    @PutMapping("/books/{isbn}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void save(@PathVariable String isbn, @RequestBody CreateBookRequest request) {
        request.isbn = isbn;
        libraryService.createBook(request, new SpringBootLogin());
    }
}
