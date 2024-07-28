package com.example.shiftproject.writers;

import com.example.shiftproject.bpp.annotations.PathWriter;
import com.example.shiftproject.bpp.annotations.StandardOpenOptionForPrintWriter;
import com.example.shiftproject.enums.Entities;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;

@Component
public class WriterFilesListProps {

    @PathWriter
    private Map<Entities, Path> paths;

    private final Path defaultPath = Path.of("default.txt");
    private final Map<Entities, PrintWriter> writersMap = new HashMap<>();

    @StandardOpenOptionForPrintWriter
    private List<StandardOpenOption> options;


    void println(Entities entity, String line) {
        if (!writersMap.containsKey(entity)) {
            Path path = paths.get(entity);
            getPrintWriter(path).ifPresent(
                    printWriter -> writersMap.put(entity, printWriter));
        }
        PrintWriter printWriter = writersMap.get(entity);
        printWriter.println(line);
        //TODO
    }

    private Optional<PrintWriter> getPrintWriter(Path path) {
        if (!Objects.nonNull(path)) {
            return Optional.empty();
        }

        PrintWriter printWriter = null;
        try {
            //Lazy write
            if (!Files.exists(path)) {
                Path parent = path.getParent();
                Files.createDirectories(parent);
                Files.createFile(path);
            }
            //If option -a exist
            if (options == null || options.isEmpty()) {
                printWriter = new PrintWriter(Files.newBufferedWriter(path, StandardCharsets.UTF_8));
            } else {
                StandardOpenOption[] standardOpenOptions = options.toArray(StandardOpenOption[]::new);
                printWriter = new PrintWriter(Files.newBufferedWriter(path, StandardCharsets.UTF_8, standardOpenOptions));
            }
        } catch (IOException e) {
            if (!path.equals(defaultPath)) {
                System.err.println("The program was unable to write the file " + path + " and will use " + defaultPath + " instead");
                printWriter = getPrintWriter(defaultPath).orElse(null);
            } else {
                System.err.println("The program was unable to write the file " + defaultPath + " and will ignore those inputs");
            }
        }
        return printWriter != null ? Optional.of(printWriter) : Optional.empty();
    }

    void close() {
        for (var entry : writersMap.entrySet()) {
            try {
                PrintWriter printWriter = entry.getValue();
                printWriter.close();
            } catch (Exception e) {
                System.err.println("The program was unable to close the file " + paths.get(entry.getKey()));
            }
        }

    }
}
