package com.example.shiftproject.stats;

import com.example.shiftproject.bpp.annotations.EntityToBeanName;
import com.example.shiftproject.enums.Entities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component("outputStatisticsFactory")
public class OutputStatisticsFactory {

    @Autowired
    public Map<String, OutputStatistics> outputStatistics;

    @EntityToBeanName
    public Map<Entities, String> entityToBeanNameMap;

    public OutputStatistics get(Entities entity) {
        String beanName = entityToBeanNameMap.get(entity);
        return outputStatistics.get(beanName);
    }

    public void printShortStatistic() {
        for (Entities entity : Entities.values()) {
            System.out.println("Количество записанных \'" + entity.NAME + "\': " + get(entity).getCount());
        }
    }

    public void printFullStatistic() {
        for (Entities entity : Entities.values()) {
            System.out.println(get(entity));
        }
    }

}
