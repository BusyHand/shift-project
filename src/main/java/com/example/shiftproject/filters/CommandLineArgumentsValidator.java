package com.example.shiftproject.filters;

import com.example.shiftproject.enums.CommandLineMeta;
import com.example.shiftproject.enums.Option;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CommandLineArgumentsValidator {

    private static final Pattern txtPatern = Pattern.compile("^[\\wа-яА-Я,\\s-]+\\.[A-Za-z]{1,4}$");


    public static String[] mapToSpringArgsValide(String[] args) {
        List<String> argsList = new ArrayList<>();
        String inputFiles = "--" + CommandLineMeta.FILENAMES_ARRAY.CHARACTER + "=";
        boolean isFilesExist = false;
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
            Option option = isOption(arg);
            if (option != null) {
                arg = "-" + arg;
                if (option.HAVE_ARGUMENT
                        && i + 1 < args.length
                        && isNotFilename(args[i + 1])) {
                    arg = arg + "=" + args[i + 1];
                    i++;
                }
                argsList.add(arg);
            } else {
                isFilesExist = true;
                inputFiles = inputFiles + arg + CommandLineMeta.DELIMITER_FILENAMES.CHARACTER;
            }
        }
        if (isFilesExist) {
            argsList.add(inputFiles);
        }
        return argsList.toArray(String[]::new);
    }

    private static boolean isNotFilename(String arg) {
        if (isOption(arg) != null) {
            return true;
        }
        if (txtPatern.matcher(arg).matches()) {
            return false;
        }
        return true;
    }

    private static Option isOption(String arg) {
        for (Option option : Option.values()) {
            if (("-" + option.COMMAND_LINE).equalsIgnoreCase(arg)) {
                return option;
            }
        }
        return null;
    }
}
