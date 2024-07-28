package com.example.shiftproject;

import com.example.shiftproject.filters.CommandLineArgumentsValidator;
import com.example.shiftproject.services.ReadFilterWriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;

@SpringBootApplication
public class ShiftProjectApplication implements CommandLineRunner {

    @Autowired
    ReadFilterWriteService readFilterWriteService;

    @Override
    public void run(String... args) {
        readFilterWriteService.readFilterWrite();
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ShiftProjectApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        args = CommandLineArgumentsValidator.mapToSpringArgsValide(args);
        app.run(args);
    }
}
