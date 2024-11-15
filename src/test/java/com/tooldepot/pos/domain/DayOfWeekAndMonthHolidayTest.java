package com.tooldepot.pos.domain;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class DayOfWeekAndMonthHolidayTest {


    @Test
    void testIsHoliday() {
        DayOfWeekAndMonthHoliday dayOfWeekAndMonthHoliday = new DayOfWeekAndMonthHoliday(
                "Labor Day", Month.SEPTEMBER, DayOfWeek.MONDAY, Holiday.AppearanceOrderInMonth.FIRST);

        assertTrue(dayOfWeekAndMonthHoliday.isObservedHolidayDate(LocalDate.of(2025, 9, 1)));
        assertTrue(dayOfWeekAndMonthHoliday.isObservedHolidayDate(LocalDate.of(2024, 9, 2)));
        assertTrue(dayOfWeekAndMonthHoliday.isObservedHolidayDate(LocalDate.of(2023, 9, 4)));
        assertTrue(dayOfWeekAndMonthHoliday.isObservedHolidayDate(LocalDate.of(2022, 9, 5)));
        assertTrue(dayOfWeekAndMonthHoliday.isObservedHolidayDate(LocalDate.of(2021, 9, 6)));
        assertTrue(dayOfWeekAndMonthHoliday.isObservedHolidayDate(LocalDate.of(2020, 9, 7)));
        assertTrue(dayOfWeekAndMonthHoliday.isObservedHolidayDate(LocalDate.of(2019, 9, 2)));
        assertTrue(dayOfWeekAndMonthHoliday.isObservedHolidayDate(LocalDate.of(2018, 9, 3)));

        for(int i = 2; i <= 30; i++) {
            assertFalse(dayOfWeekAndMonthHoliday.isObservedHolidayDate(LocalDate.of(2025, 9, i)));
        }
    }

    @Test
    void testCalculateObservedDateFirstInMonth() {
        DayOfWeekAndMonthHoliday dayOfWeekAndMonthHoliday = new DayOfWeekAndMonthHoliday(
                "Labor Day", Month.SEPTEMBER, DayOfWeek.MONDAY, Holiday.AppearanceOrderInMonth.FIRST);

        assertEquals(LocalDate.of(2025, 9, 1), dayOfWeekAndMonthHoliday.calculateObservedDate(2025));
        assertEquals(LocalDate.of(2024, 9, 2), dayOfWeekAndMonthHoliday.calculateObservedDate(2024));
        assertEquals(LocalDate.of(2023, 9, 4), dayOfWeekAndMonthHoliday.calculateObservedDate(2023));
        assertEquals(LocalDate.of(2022, 9, 5), dayOfWeekAndMonthHoliday.calculateObservedDate(2022));
        assertEquals(LocalDate.of(2021, 9, 6), dayOfWeekAndMonthHoliday.calculateObservedDate(2021));
        assertEquals(LocalDate.of(2020, 9, 7), dayOfWeekAndMonthHoliday.calculateObservedDate(2020));
        assertEquals(LocalDate.of(2019, 9, 2), dayOfWeekAndMonthHoliday.calculateObservedDate(2019));
        assertEquals(LocalDate.of(2018, 9, 3), dayOfWeekAndMonthHoliday.calculateObservedDate(2018));
    }

    @Test
    void testCalculateObservedDateSecondInMonth() {
        DayOfWeekAndMonthHoliday dayOfWeekAndMonthHoliday = new DayOfWeekAndMonthHoliday(
                "2nd Tue Holiday", Month.SEPTEMBER, DayOfWeek.TUESDAY, Holiday.AppearanceOrderInMonth.SECOND);

        assertEquals(LocalDate.of(2025, 9, 9), dayOfWeekAndMonthHoliday.calculateObservedDate(2025));
        assertEquals(LocalDate.of(2024, 9, 10), dayOfWeekAndMonthHoliday.calculateObservedDate(2024));
        assertEquals(LocalDate.of(2023, 9, 12), dayOfWeekAndMonthHoliday.calculateObservedDate(2023));
        assertEquals(LocalDate.of(2022, 9, 13), dayOfWeekAndMonthHoliday.calculateObservedDate(2022));
        assertEquals(LocalDate.of(2021, 9, 14), dayOfWeekAndMonthHoliday.calculateObservedDate(2021));
        assertEquals(LocalDate.of(2020, 9, 8), dayOfWeekAndMonthHoliday.calculateObservedDate(2020));
        assertEquals(LocalDate.of(2019, 9, 10), dayOfWeekAndMonthHoliday.calculateObservedDate(2019));
        assertEquals(LocalDate.of(2018, 9, 11), dayOfWeekAndMonthHoliday.calculateObservedDate(2018));
    }

    @Test
    void testCalculateObservedDateLastInMonth() {
        DayOfWeekAndMonthHoliday dayOfWeekAndMonthHoliday = new DayOfWeekAndMonthHoliday(
                "Last Thu Holiday", Month.NOVEMBER, DayOfWeek.THURSDAY, Holiday.AppearanceOrderInMonth.LAST);

        assertEquals(LocalDate.of(2025, 11, 27), dayOfWeekAndMonthHoliday.calculateObservedDate(2025));
        assertEquals(LocalDate.of(2024, 11, 28), dayOfWeekAndMonthHoliday.calculateObservedDate(2024));

        assertEquals(LocalDate.of(2011, 11, 24), dayOfWeekAndMonthHoliday.calculateObservedDate(2011));
        assertEquals(LocalDate.of(2012, 11, 29), dayOfWeekAndMonthHoliday.calculateObservedDate(2012));
        assertEquals(LocalDate.of(2013, 11, 28), dayOfWeekAndMonthHoliday.calculateObservedDate(2013));
        assertEquals(LocalDate.of(2014, 11, 27), dayOfWeekAndMonthHoliday.calculateObservedDate(2014));
        assertEquals(LocalDate.of(2015, 11, 26), dayOfWeekAndMonthHoliday.calculateObservedDate(2015));
        assertEquals(LocalDate.of(2016, 11, 24), dayOfWeekAndMonthHoliday.calculateObservedDate(2016));
        assertEquals(LocalDate.of(2017, 11, 30), dayOfWeekAndMonthHoliday.calculateObservedDate(2017));
        assertEquals(LocalDate.of(2018, 11, 29), dayOfWeekAndMonthHoliday.calculateObservedDate(2018));
        assertEquals(LocalDate.of(2019, 11, 28), dayOfWeekAndMonthHoliday.calculateObservedDate(2019));
    }
}