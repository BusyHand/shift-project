package com.example.shiftproject.enums;

import java.util.regex.Pattern;

public enum Entities {
    INTEGER("Целые числа", "-?\\d+", "integer.txt"),
    DOUBLE("Числа с плавающей точкой", "-?(\\d+\\.\\d+([eE][-+]?\\d+)?)", "floats.txt"),
    STRING("Строки", "[a-zA-Zа-яА-Я\\s]+", "strings.txt");

    public final Pattern PATTERN;

    public final String REGEX;

    public final String OUTPUT_FILENAME;

    public final String NAME;


    Entities(String name, String regex, String output_filename) {
        NAME = name;
        REGEX = regex;
        PATTERN = Pattern.compile(regex, Pattern.MULTILINE);
        OUTPUT_FILENAME = output_filename;
    }
}
