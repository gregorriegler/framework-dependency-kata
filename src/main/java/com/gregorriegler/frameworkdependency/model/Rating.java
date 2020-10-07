package com.gregorriegler.frameworkdependency.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
