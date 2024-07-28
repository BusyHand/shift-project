package com.example.shiftproject.readers;

import java.io.IOException;

public interface ReaderFiles extends AutoCloseable {

    String readLine();

}
