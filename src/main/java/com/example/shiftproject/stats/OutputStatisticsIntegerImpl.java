package com.example.shiftproject.stats;

import com.example.shiftproject.bpp.annotations.PutEntityToStatistic;
import com.example.shiftproject.enums.Entities;
import org.springframework.stereotype.Component;

import java.math.BigInteger;

@PutEntityToStatistic(Entities.INTEGER)
@Component
public class OutputStatisticsIntegerImpl implements OutputStatistics {
    private BigInteger sum = BigInteger.ZERO;
    ;
    private BigInteger max;
    private BigInteger min;
    private BigInteger count = BigInteger.ZERO;


    @Override
    public void refresh(String other) {
        refresh(new BigInteger(other));
    }

    @Override
    public String getCount() {
        return count.toString();
    }

    private void refresh(BigInteger other) {
        if (max == null || max.compareTo(other) < 0) max = other;
        if (min == null || min.compareTo(other) > 0) min = other;
        sum = sum == null ? other : sum.add(other);
        count = count == null ? BigInteger.ONE : count.add(BigInteger.ONE);
    }

    public BigInteger getAverage() {
        return count.equals(BigInteger.ZERO) ? BigInteger.ZERO
                : sum.divide(count);
    }

    public BigInteger getMin() {
        return min != null ? min : BigInteger.ZERO;
    }

    public BigInteger getMax() {
        return max != null ? max : BigInteger.ZERO;
    }

    @Override
    public String toString() {
        return "Целые числа: " + "\n" +
                "Сумма записанных чисел: " + sum + "\n" +
                "Максимальное число из записанных чисел: " + getMax() + "\n" +
                "Минимальное число из записанных чисел: " + getMin() + "\n" +
                "Среднее из записанных чисел: " + getAverage() + "\n" +
                "Количество записанных чисел: " + count + "\n";
    }

}
