package com.example.shiftproject.writers;

import com.example.shiftproject.enums.Entities;

public interface WriterFiles extends AutoCloseable {

    void println(Entities e, String line);
}
