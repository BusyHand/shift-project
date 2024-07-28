package com.example.shiftproject.services;

import com.example.shiftproject.enums.Entities;
import com.example.shiftproject.filters.MatcherEntity;
import com.example.shiftproject.readers.ReaderFiles;
import com.example.shiftproject.writers.WriterFiles;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReadFilterWriteService {

    private final ReaderFiles readerFiles;
    private final WriterFiles writerFiles;
    private final MatcherEntity matcher = new MatcherEntity();

    public void readFilterWrite() {
        try (readerFiles; writerFiles) {
            String line;
            while ((line = readerFiles.readLine()) != null) {
                Entities entity = matcher.match(line);
                writerFiles.println(entity, line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
