package com.example.infinispantask.entities;

public enum Degree { // converter is in /util
    ASSISTANT(0),
    ASSOCIATE_PROFESSOR(1),
    PROFESSOR(2);
    private final Integer code;

    Degree(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
