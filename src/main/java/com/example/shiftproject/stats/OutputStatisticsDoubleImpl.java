package com.example.shiftproject.stats;

import com.example.shiftproject.bpp.annotations.PutEntityToStatistic;
import com.example.shiftproject.enums.Entities;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

@PutEntityToStatistic(Entities.DOUBLE)
@Component
public class OutputStatisticsDoubleImpl implements OutputStatistics {
    private BigDecimal sum = BigDecimal.ZERO;
    private BigDecimal max;
    private BigDecimal min;
    private BigDecimal count = BigDecimal.ZERO;

    @Override
    public void refresh(String other) {
        refresh(new BigDecimal(other));
    }

    @Override
    public String getCount() {
        return count.toString();
    }

    private void refresh(BigDecimal other) {
        if (max == null || max.compareTo(other) < 0) max = other;
        if (min == null || min.compareTo(other) > 0) min = other;
        sum = sum == null ? other : sum.add(other);
        count = count == null ? BigDecimal.ONE : count.add(BigDecimal.ONE);
    }

    public BigDecimal getAverage() {
        return count.equals(BigDecimal.ZERO) ? BigDecimal.ZERO
                : sum.divide(count, 2, RoundingMode.UP);
    }

    public BigDecimal getMin() {
        return min != null ? min : BigDecimal.ZERO;
    }

    public BigDecimal getMax() {
        return max != null ? max : BigDecimal.ZERO;
    }


    @Override
    public String toString() {
        return "Числа с плавающей точкой: " + "\n" +
                "Сумма записанных чисел: " + sum + "\n" +
                "Максимальное число из записанных чисел: " + getMax() + "\n" +
                "Минимальное число из записанных чисел: " + getMin() + "\n" +
                "Среднее из записанных чисел: " + getAverage() + "\n" +
                "Количество записанных чисел: " + count + "\n";
    }

}
