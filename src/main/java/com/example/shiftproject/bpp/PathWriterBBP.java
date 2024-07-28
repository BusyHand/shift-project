package com.example.shiftproject.bpp;

import com.example.shiftproject.bpp.annotations.PathWriter;
import com.example.shiftproject.enums.Entities;
import com.example.shiftproject.enums.Option;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PathWriterBBP implements BeanPostProcessor {

    private final ApplicationArguments applicationArguments;

    private Map<Entities, Path> pathsMap = new HashMap<>();

    private boolean alreadyChecked = false;
    private String prefix = "";
    private String path = "";

    @SneakyThrows
    @Override
    @SuppressWarnings("unchecked")
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (!alreadyChecked) {
            alreadyChecked = true;
            if (applicationArguments.containsOption(Option.PREFIX_OUTPUT_NAME.COMMAND_LINE)) {
                List<String> prefixOutputName = applicationArguments.getOptionValues(Option.PREFIX_OUTPUT_NAME.COMMAND_LINE);
                if (!prefixOutputName.isEmpty()) {
                    prefix = prefixOutputName.get(0);
                }
            }

            if (applicationArguments.containsOption(Option.PATH.COMMAND_LINE)) {
                List<String> pathToFile = applicationArguments.getOptionValues(Option.PATH.COMMAND_LINE);
                if (!pathToFile.isEmpty()) {
                    path = pathToFile.get(0);
                }
            }
        }

        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(PathWriter.class)) {
                for (Entities entity : Entities.values()) {
                    Path pathFile = Path.of(System.getProperty("user.dir"),
                            path,
                            prefix + entity.OUTPUT_FILENAME);
                    pathsMap.put(entity, pathFile);
                }
                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, pathsMap);
            }
        }
        return bean;
    }
}
