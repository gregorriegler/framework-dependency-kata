package com.gregorriegler.frameworkdependency.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
@Access(AccessType.FIELD)
public class Book {
    @Id
    public String isbn;
    public String title;
    public String author;

    /**
     * for hibernate
     */
    public Book() {
    }

    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }
}
