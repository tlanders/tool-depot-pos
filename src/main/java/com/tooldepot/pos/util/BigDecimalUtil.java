package com.tooldepot.pos.util;

import java.math.BigDecimal;

public class BigDecimalUtil {
    // don't allow instantiation
    private BigDecimalUtil() {
    }

    public static BigDecimal newBD(String value) {
        return new BigDecimal(value);
    }
    public static BigDecimal newBD(int value) {
        return BigDecimal.valueOf(value);
    }
}
