package com.tooldepot.pos.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtil {
    public static final BigDecimal BD_100 = newBD(100);

    // don't allow instantiation
    private BigDecimalUtil() {
    }

    public static BigDecimal newBD(String value) {
        return new BigDecimal(value);
    }
    public static BigDecimal newBD(int value) {
        return BigDecimal.valueOf(value);
    }

    public static BigDecimal multiply(BigDecimal value1, BigDecimal value2, int scale) {
        return value1.multiply(value2).setScale(scale, RoundingMode.HALF_UP);
    }

    public static BigDecimal multiply(BigDecimal value1, int intVal2, int scale) {
        return value1.multiply(newBD(intVal2)).setScale(scale, RoundingMode.HALF_UP);
    }

    public static BigDecimal divide(int dividend, BigDecimal divisor, int scale) {
        return newBD(dividend).divide(divisor, scale, RoundingMode.HALF_UP);
    }
}
