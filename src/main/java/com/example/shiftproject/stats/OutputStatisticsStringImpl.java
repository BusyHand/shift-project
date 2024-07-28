package com.example.shiftproject.stats;

import com.example.shiftproject.bpp.annotations.PutEntityToStatistic;
import com.example.shiftproject.enums.Entities;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@Component
@PutEntityToStatistic(Entities.STRING)
public class OutputStatisticsStringImpl implements OutputStatistics {
    private String max;
    private String min;


    private BigInteger count = BigInteger.ZERO;

    @Override
    public void refresh(String other) {
        if (max == null || max.length() < other.length()) max = other;
        if (min == null || min.length() > other.length()) min = other;
        count = count == null ? BigInteger.ONE : count.add(BigInteger.ONE);
    }

    @Override
    public String getCount() {
        return count.toString();
    }

    public String getMax() {
        return max != null ? max : "";
    }

    public String getMin() {
        return min != null ? min : "";
    }

    @Override
    public String toString() {
        return "Строки: " + "\n" +
                "Самая длинная строчка: (длина " + getMax().length() + " символа ) " + "\'" + getMax() + "\'" + "\n" +
                "Самая короткая строчка: (длина " + getMin().length() + " символа ) " + "\'" + getMin() + "\'" + " " + "\n" +
                "Количество записанных строчек в файл: " + count + "\n";
    }

}
