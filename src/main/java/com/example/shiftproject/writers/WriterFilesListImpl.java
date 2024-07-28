package com.example.shiftproject.writers;

import com.example.shiftproject.bpp.annotations.OutputStatisticsTracker;
import com.example.shiftproject.enums.Entities;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@OutputStatisticsTracker
public class WriterFilesListImpl implements WriterFiles {

    private final WriterFilesListProps writerFilesProps;


    @Override
    public void println(Entities entity, String line) {
        writerFilesProps.println(entity, line);
    }

    @Override
    public void close() {
        writerFilesProps.close();
    }
}
