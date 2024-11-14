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
public class ExactDayHoliday implements Holiday {
    private final Month month;
    private final int dayOfMonth;
    private final String name;
    private final AppearanceModifier appearanceModifier;

    public ExactDayHoliday(String name, Month month, int day, AppearanceModifier appearanceModifier) {
        this.name = name;
        this.month = month;
        this.dayOfMonth = day;
        this.appearanceModifier = appearanceModifier;
    }

    @Override
    public boolean isHoliday(LocalDate date) {
        if(appearanceModifier == AppearanceModifier.CLOSEST_WEEKDAY) {
            LocalDate holidayDate = LocalDate.of(date.getYear(), month, dayOfMonth);
            LocalDate observedDate = holidayDate;
            if(holidayDate.getDayOfWeek() == DayOfWeek.SATURDAY) {
                observedDate = holidayDate.minusDays(1);
            } else if(holidayDate.getDayOfWeek() == DayOfWeek.SUNDAY) {
                observedDate = holidayDate.plusDays(1);
            }
            return observedDate.equals(date);
        } else {
            return date.getMonth() == month && date.getDayOfMonth() == dayOfMonth;
        }
    }
}
