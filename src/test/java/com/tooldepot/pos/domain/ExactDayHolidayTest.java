package com.tooldepot.pos.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static com.tooldepot.pos.domain.Holiday.AppearanceModifier.*;
import static org.junit.jupiter.api.Assertions.*;

class ExactDayHolidayTest {

    @Test
    void testIsObservedHolidayDate_invalidInputs() {
        ExactDayHoliday holiday = new ExactDayHoliday("July 4th", Month.JULY, 4, CLOSEST_WEEKDAY);
        assertThrows(NullPointerException.class, () -> holiday.isObservedHolidayDate(null));

        assertThrows(NullPointerException.class, () -> new ExactDayHoliday(null, Month.SEPTEMBER, 2, CLOSEST_WEEKDAY));
        assertThrows(NullPointerException.class, () -> new ExactDayHoliday("name", null, 2, CLOSEST_WEEKDAY));
        assertThrows(NullPointerException.class, () -> new ExactDayHoliday("name", Month.JULY, 2, null));
        assertThrows(IllegalArgumentException.class, () -> new ExactDayHoliday("name", Month.JULY, 0, NONE));
        assertThrows(IllegalArgumentException.class, () -> new ExactDayHoliday("name", Month.JULY, 32, NONE));
    }

    @Test
    void testIsObservedHolidayDate() {
        ExactDayHoliday holiday = new ExactDayHoliday("July 4th", Month.JULY, 4, CLOSEST_WEEKDAY);

        assertTrue(holiday.isObservedHolidayDate(LocalDate.of(2015, Month.JULY, 3)));
        assertTrue(holiday.isObservedHolidayDate(LocalDate.of(2016, Month.JULY, 4)));
        assertTrue(holiday.isObservedHolidayDate(LocalDate.of(2017, Month.JULY, 4)));
        assertTrue(holiday.isObservedHolidayDate(LocalDate.of(2018, Month.JULY, 4)));
        assertTrue(holiday.isObservedHolidayDate(LocalDate.of(2019, Month.JULY, 4)));
        assertTrue(holiday.isObservedHolidayDate(LocalDate.of(2020, Month.JULY, 3)));
        assertTrue(holiday.isObservedHolidayDate(LocalDate.of(2021, Month.JULY, 5)));
        assertTrue(holiday.isObservedHolidayDate(LocalDate.of(2022, Month.JULY, 4)));
        assertTrue(holiday.isObservedHolidayDate(LocalDate.of(2023, Month.JULY, 4)));
        assertTrue(holiday.isObservedHolidayDate(LocalDate.of(2024, Month.JULY, 4)));

        for(int day = 1; day <= 31; day++) {
            if(day != 3) {
                assertFalse(holiday.isObservedHolidayDate(LocalDate.of(2015, Month.JULY, day)));
            }
        }
    }
}