package com.tooldepot.pos.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

class ExactDayHolidayTest {

    @Test
    void testIsObservedHolidayDate() {
        ExactDayHoliday holiday = new ExactDayHoliday("July 4th", Month.JULY, 4, Holiday.AppearanceModifier.CLOSEST_WEEKDAY);

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