package com.gregorriegler.frameworkdependency.model;

public class RatingRequest {
    public Stars stars;
    public String comment;

    public enum Stars {
        ONE, TWO, THREE, FOUR, FIVE
    }
}
