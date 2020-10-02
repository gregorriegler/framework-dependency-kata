package com.gregorriegler.frameworkdependency.model;

import com.gregorriegler.frameworkdependency.repository.BookRepository;
import com.gregorriegler.frameworkdependency.web.CreateBookRequest;
import com.gregorriegler.frameworkdependency.web.RatingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

@Service
public class LibraryService {

    @Autowired
    private SecurityService securityService;

    @Autowired
    private BookRepository repository;

    public ResponseEntity<Collection<Book>> getAllBooks() {
        if (securityService.isAuthenticated() && (securityService.isAdmin() || securityService.isUser())) {
            return ResponseEntity.ok(repository.findAll());
        } else {
            throw new AccessDeniedException("access denied!");
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public void save(String isbn, CreateBookRequest request) {
        Book book = repository.findById(isbn).orElseGet(Book::new);
        book.isbn = isbn;
        book.title = request.title;
        book.author = request.author;
        repository.save(book);
    }

    @PreAuthorize("hasRole('USER')")
    @Transactional
    public void rate(String isbn, Authentication authentication, RatingRequest request) {
        repository.findById(isbn).ifPresent(book -> {
            Rating rating = new Rating();
            rating.user = authentication.getName();
            rating.stars = request.stars;
            rating.comment = request.comment;
            book.ratings.add(rating);
        });
    }
}
