package com.gregorriegler.frameworkdependency.model;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
@Access(AccessType.FIELD)
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    public String user;

    @Enumerated(EnumType.STRING)
    public RatingRequest.Stars stars;

    public String comment;
}
