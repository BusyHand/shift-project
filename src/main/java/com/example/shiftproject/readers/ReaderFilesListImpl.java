package com.example.shiftproject.readers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class ReaderFilesListImpl implements ReaderFiles {

    private final ReaderFilesListProps readerFilesListProps;

    @Override
    public String readLine() {
            return readerFilesListProps.readLine();
    }

    @Override
    public void close() {
        readerFilesListProps.close();
    }

}
