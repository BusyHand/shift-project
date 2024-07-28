package com.example.shiftproject.bpp;

import com.example.shiftproject.bpp.annotations.EntityToBeanName;
import com.example.shiftproject.bpp.annotations.PutEntityToStatistic;
import com.example.shiftproject.enums.Entities;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class EntityToBeanNameBBP implements BeanPostProcessor {

    private Map<Entities, String> map = new HashMap<>();


    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        Class<?> beanClass = bean.getClass();
        if (beanClass.isAnnotationPresent(PutEntityToStatistic.class)) {
            PutEntityToStatistic annotation = beanClass.getAnnotation(PutEntityToStatistic.class);
            map.put(annotation.value(), beanName);
        }
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(EntityToBeanName.class)) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, map);
            }
        }
        return bean;
    }
}
