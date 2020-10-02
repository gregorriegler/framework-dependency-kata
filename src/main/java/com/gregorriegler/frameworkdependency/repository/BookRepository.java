package com.gregorriegler.frameworkdependency.repository;

import com.gregorriegler.frameworkdependency.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, String> {
}
