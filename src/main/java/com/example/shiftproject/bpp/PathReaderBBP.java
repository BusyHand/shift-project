package com.example.shiftproject.bpp;

import com.example.shiftproject.bpp.annotations.PathReader;
import com.example.shiftproject.enums.CommandLineMeta;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PathReaderBBP implements BeanPostProcessor {

    private final ApplicationArguments applicationArguments;

    private final List<Path> paths = new ArrayList<>();

    private boolean alreadyChecked = false;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (!alreadyChecked && applicationArguments.containsOption(CommandLineMeta.FILENAMES_ARRAY.CHARACTER)) {
            alreadyChecked = true;
            String[] files = applicationArguments.getOptionValues(CommandLineMeta.FILENAMES_ARRAY.CHARACTER)
                    .get(0)
                    .split(CommandLineMeta.DELIMITER_FILENAMES.CHARACTER);
            for (String file : files) {
                Path path = Path.of(System.getProperty("user.dir"), file);
                if (Files.exists(path)) {
                    paths.add(path);
                } else {
                    System.err.println("File " + path + " not exists");
                }
            }
        }
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(PathReader.class)) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, paths);
            }
        }
        return bean;
    }
}
