package com.gregorriegler.frameworkdependency.model;

import com.gregorriegler.frameworkdependency.repository.BookRepository;
import com.gregorriegler.frameworkdependency.web.CreateBookRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class LibraryService {

    private static final Logger LOG = LoggerFactory.getLogger(LibraryService.class);

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
    public void createBook(String isbn, CreateBookRequest request) {
        Book book = repository.findById(isbn).orElseGet(Book::new);
        book.isbn = isbn;
        book.title = request.title;
        book.author = request.author;
        repository.save(book);
    }

    @Scheduled(fixedRate = 25000)
    public void createBookReport() throws IOException {
        String report = repository.findAll().stream()
            .map(book -> book.isbn + "," + book.title)
            .collect(Collectors.joining("\n"));
        File tmpFile = File.createTempFile("book_report", ".csv");
        FileWriter writer = new FileWriter(tmpFile);
        writer.write(report);
        writer.close();
        LOG.info("wrote book report to {}", tmpFile.toString());
    }
}
