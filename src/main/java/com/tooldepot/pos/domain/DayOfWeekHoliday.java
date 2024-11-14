package com.tooldepot.pos.domain;

import lombok.Getter;
import lombok.ToString;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;

/**
 * An ExactDayHoliday falls on the same day and month every year.
 */
@Getter
@ToString
public class DayOfWeekHoliday implements Holiday {
    private final Month month;
    private final DayOfWeek dayOfWeek;
    private final String name;
    private final AppearanceOrderInMonth appearanceOrderInMonth;

    public DayOfWeekHoliday(String name, Month month, DayOfWeek dayOfWeek,
                            AppearanceOrderInMonth appearanceOrderInMonth) {
        this.name = name;
        this.month = month;
        this.dayOfWeek = dayOfWeek;
        this.appearanceOrderInMonth = appearanceOrderInMonth;
    }

    @Override
    public boolean isHoliday(LocalDate date) {
        return false;
    }

}
