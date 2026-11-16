package com.gregorriegler.frameworkdependency.services;

import com.gregorriegler.frameworkdependency.model.Rating;
import com.gregorriegler.frameworkdependency.dtos.request.RatingRequest;
import com.gregorriegler.frameworkdependency.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RatingService {

    @Autowired
    private BookRepository repository;

    @PostMapping("/books/{isbn}/ratings")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('USER')")
    @Transactional
    public void rate(@PathVariable String isbn, Authentication authentication, @RequestBody RatingRequest request) {
        repository.findById(isbn).ifPresent(book -> {
            Rating rating = new Rating();
            rating.user = authentication.getName();
            rating.stars = request.stars;
            rating.comment = request.comment;
            book.ratings.add(rating);
        });
    }
}
