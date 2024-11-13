package com.tooldepot.pos.util;

import org.junit.jupiter.api.Test;

import static com.tooldepot.pos.util.BigDecimalUtil.*;
import static org.junit.jupiter.api.Assertions.*;

public class BigDecimalUtilTest {
    @Test
    void testMultiplyBDbyInt() {
        assertEquals(newBD("6.00"), multiply(newBD("2.00"), 3, 2));
        assertEquals(newBD("7.50"), multiply(newBD("2.50"), 3, 2));
        assertEquals(newBD("7.53"), multiply(newBD("2.51"), 3, 2));
        assertEquals(newBD("7.530"), multiply(newBD("2.51"), 3, 3));
        assertEquals(newBD("7.5"), multiply(newBD("2.51"), 3, 1));
        assertEquals(newBD("7.6"), multiply(newBD("2.52"), 3, 1));
        assertEquals(newBD("8"), multiply(newBD("2.52"), 3, 0));
        assertEquals(newBD("0"), multiply(newBD("2.52"), 0, 0));
        assertEquals(newBD("0.0"), multiply(newBD("2.52"), 0, 1));
        assertEquals(newBD("0.00000"), multiply(newBD("2.52"), 0, 5));
    }

    @Test
    void testMultiplyBDbyBD() {
        assertEquals(newBD("6.00"), multiply(newBD("2.00"), newBD("3"), 2));
        assertEquals(newBD("6.00"), multiply(newBD("2.00"), newBD("3.0"), 2));
        assertEquals(newBD("6.000"), multiply(newBD("2.00"), newBD("3.0"), 3));
        assertEquals(newBD("6"), multiply(newBD("2.00"), newBD("3.0"), 0));
        assertEquals(newBD("6.0"), multiply(newBD("2.0011"), newBD("3.011"), 1));
        assertEquals(newBD("0.5"), multiply(newBD("1"), newBD("0.50"), 1));
        assertEquals(newBD("0.250"), multiply(newBD("1"), newBD("0.25"), 3));
        assertEquals(newBD("0.1"), multiply(newBD("0.5"), newBD("0.25"), 1));
        assertEquals(newBD("0.125"), multiply(newBD("0.5"), newBD("0.25"), 3));
        assertEquals(newBD("0.2"), multiply(newBD("0.7"), newBD("0.25"), 1));

        assertEquals(newBD("-6.00"), multiply(newBD("-2.00"), newBD("3"), 2));
        assertEquals(newBD("-6.00"), multiply(newBD("2.00"), newBD("-3.0"), 2));
        assertEquals(newBD("-6.000"), multiply(newBD("2.00"), newBD("-3.0"), 3));
        assertEquals(newBD("-6"), multiply(newBD("-2.00"), newBD("3.0"), 0));
        assertEquals(newBD("-6.0"), multiply(newBD("2.0011"), newBD("-3.011"), 1));

        assertEquals(newBD("6.00"), multiply(newBD("-2.00"), newBD("-3"), 2));
        assertEquals(newBD("6.00"), multiply(newBD("-2.00"), newBD("-3.0"), 2));
        assertEquals(newBD("6.000"), multiply(newBD("-2.00"), newBD("-3.0"), 3));
        assertEquals(newBD("6"), multiply(newBD("-2.00"), newBD("-3.0"), 0));
        assertEquals(newBD("6.0"), multiply(newBD("-2.0011"), newBD("-3.011"), 1));
    }

    @Test
    public void testDivideIntbyBD() {
        assertEquals(newBD("2.00"), divide(6, newBD("3.00"), 2));
        assertEquals(newBD("2.0"), divide(6, newBD("3.00"), 1));
        assertEquals(newBD("2"), divide(6, newBD("3.00"), 0));
        assertEquals(newBD("1.3"), divide(4, newBD("3.00"), 1));
        assertEquals(newBD("1.7"), divide(5, newBD("3.00"), 1));

        assertEquals(newBD("0"), divide(0, newBD("3.00"), 0));
        assertEquals(newBD("0.00"), divide(0, newBD("-3.00"), 2));

        assertEquals(newBD("2.00"), divide(1, newBD("0.5"), 2));
        assertEquals(newBD("4.0"), divide(1, newBD("0.25"), 1));

        assertEquals(newBD("-1.3"), divide(-4, newBD("3.00"), 1));
        assertEquals(newBD("-1.7"), divide(5, newBD("-3.00"), 1));
        assertEquals(newBD("-2.00"), divide(-1, newBD("0.5"), 2));
        assertEquals(newBD("-4.0"), divide(1, newBD("-0.25"), 1));

        assertEquals(newBD("1.3"), divide(-4, newBD("-3.00"), 1));
        assertEquals(newBD("1.7"), divide(-5, newBD("-3.00"), 1));
        assertEquals(newBD("2.00"), divide(-1, newBD("-0.5"), 2));
        assertEquals(newBD("4.0"), divide(-1, newBD("-0.25"), 1));

        assertThrows(ArithmeticException.class, () -> divide(6, newBD("0.00"), 2));
        assertThrows(ArithmeticException.class, () -> divide(6, newBD("0"), 0));
    }
}
