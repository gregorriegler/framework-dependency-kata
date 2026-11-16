package com.gregorriegler.frameworkdependency.model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Access(AccessType.FIELD)
public class Book {
    @Id
    public String isbn;
    public String title;
    public String author;

    @OneToMany(cascade = CascadeType.ALL)
    public List<Rating> ratings = new ArrayList<>();

    public Book() {
    }

    public Book(String isbn, String title, String author) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
    }
}
