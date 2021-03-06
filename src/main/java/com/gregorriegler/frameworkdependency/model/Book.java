package com.gregorriegler.frameworkdependency.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
