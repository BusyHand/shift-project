package com.example.shiftproject.enums;

import java.util.UUID;

public enum CommandLineMeta {

    DELIMITER_FILENAMES(UUID.randomUUID().toString()),
    FILENAMES_ARRAY(UUID.randomUUID().toString());

    public final String CHARACTER;

    CommandLineMeta(String character) {
        this.CHARACTER = character;
    }
}
