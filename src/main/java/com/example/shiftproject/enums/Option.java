package com.example.shiftproject.enums;


public enum Option {
    PATH("o", true),
    PREFIX_OUTPUT_NAME("p", true),

    APPEND_TO_FILE("a", false),
    SHORT_STATISTICS("s", false),
    FULL_STATISTICS("f", false);
    public final String COMMAND_LINE;
    public final boolean HAVE_ARGUMENT;

    Option(String command_line, boolean have_argument) {
        COMMAND_LINE = command_line;
        HAVE_ARGUMENT = have_argument;
    }
}
