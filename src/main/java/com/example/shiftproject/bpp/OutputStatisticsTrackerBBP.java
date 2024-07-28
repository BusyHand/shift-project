package com.example.shiftproject.bpp;

import com.example.shiftproject.enums.Entities;
import com.example.shiftproject.enums.Option;
import com.example.shiftproject.bpp.annotations.OutputStatisticsTracker;
import com.example.shiftproject.stats.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class OutputStatisticsTrackerBBP implements BeanPostProcessor {

    private final ApplicationArguments applicationArguments;
    private final ApplicationContext applicationContext;

    private Map<String, Class> map = new HashMap<>();

    private boolean isFullStatistic = false;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        if ((isFullStatistic = applicationArguments.containsOption(Option.FULL_STATISTICS.COMMAND_LINE))
                || applicationArguments.containsOption(Option.SHORT_STATISTICS.COMMAND_LINE)) {

            Class<?> beanClass = bean.getClass();
            if (beanClass.isAnnotationPresent(OutputStatisticsTracker.class)) {
                map.put(beanName, beanClass);
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        if (map.containsKey(beanName)) {
            Class beanClass = map.get(beanName);
            OutputStatisticsFactory outputStatisticsFactory = (OutputStatisticsFactory) applicationContext.getBean("outputStatisticsFactory");
            return Proxy.newProxyInstance(beanClass.getClassLoader(),
                    beanClass.getInterfaces(),
                    (proxy, method, args) -> {
                        method.invoke(bean, args);
                        if (method.getName().equalsIgnoreCase("println")) {
                            OutputStatistics outputStatistics = outputStatisticsFactory.get((Entities) args[0]);
                            outputStatistics.refresh((String) args[1]);
                        }
                        if (method.getName().equalsIgnoreCase("close")) {
                            if (isFullStatistic) {
                                outputStatisticsFactory.printFullStatistic();
                            } else {
                                outputStatisticsFactory.printShortStatistic();
                            }
                        }
                        return null;
                    });

        }
        return bean;
    }
}
