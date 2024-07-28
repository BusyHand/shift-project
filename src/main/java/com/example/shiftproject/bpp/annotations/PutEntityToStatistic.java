package com.example.shiftproject.bpp.annotations;

import com.example.shiftproject.enums.Entities;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PutEntityToStatistic {
    Entities value();
}
