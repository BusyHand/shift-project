package com.example.shiftproject.filters;


import com.example.shiftproject.enums.Entities;

public class MatcherEntity {
    private final Entities[] entities = Entities.values();

    public Entities match(String line) {
        for (var entity : entities) {
            if (entity.PATTERN.matcher(line).matches()) {
                return entity;
            }
        }
        return null;
    }
}
