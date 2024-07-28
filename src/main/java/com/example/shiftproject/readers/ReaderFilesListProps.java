package com.example.shiftproject.readers;

import com.example.shiftproject.bpp.annotations.PathReader;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ReaderFilesListProps {

    @PathReader
    private List<Path> paths;
    private final List<AccReader> readersList = new ArrayList<>();

    private final Map<AccReader, Path> readersToPathMap = new HashMap<>();
    private int currentIndex = 0;

    private static class AccReader extends BufferedReader {

        boolean isStop;

        private AccReader(Reader in) {
            super(in);
        }
    }


    @PostConstruct
    private void initReaders() {
        for (Path path : paths) {
            try {
                AccReader accReader = new AccReader(Files.newBufferedReader(path, StandardCharsets.UTF_8));
                readersList.add(accReader);
                readersToPathMap.put(accReader, path);
            } catch (IOException e) {
                System.err.println("The program was unable to read the file " + path + " and will continue to run without it");
            }
        }
    }

    String readLine() {
        //If list is empty
        if (readersList.isEmpty()) {
            return null;
        }

        AccReader currentReader;
        int tempIndex = currentIndex;
        String line;

        do {
            currentReader = readersList.get(tempIndex);
            if ((line = readLine(currentReader)) == null) {
                currentReader.isStop = true;
            }
            tempIndex = (tempIndex + 1) % readersList.size();
            //If you have gone through the entire list
            if (tempIndex == currentIndex) {
                break;
            }
        } while (currentReader.isStop);

        currentIndex = tempIndex;
        return line;
    }

    private String readLine(AccReader currentReader) {
        try {
            return currentReader.readLine();
        } catch (IOException e) {
            System.err.println("The program was unable to read the file " + readersToPathMap.get(currentReader) + " and will continue to run without it");
            return null;
        }
    }

    void close() {
        for (var currentReader : readersList) {
            try {
                currentReader.close();
            } catch (IOException e) {
                System.err.println("The program was unable to close the file " + readersToPathMap.get(currentReader));
            }
        }

    }
}
