package com.example.shiftproject.bpp;

import com.example.shiftproject.bpp.annotations.StandardOpenOptionForPrintWriter;
import com.example.shiftproject.enums.Option;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.nio.file.OpenOption;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StandardOpenOptionForPrintWriterBBP implements BeanPostProcessor {

    private final ApplicationArguments applicationArguments;

    private final List<OpenOption> options = List.of(StandardOpenOption.APPEND);

    private boolean isFirst = false;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if (!isFirst && applicationArguments.containsOption(Option.APPEND_TO_FILE.COMMAND_LINE)) {
            isFirst = true;
        }
        if (isFirst) {
            Field[] fields = bean.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(StandardOpenOptionForPrintWriter.class)) {
                    field.setAccessible(true);
                    ReflectionUtils.setField(field, bean, options);
                }
            }
        }
        return bean;
    }
}
