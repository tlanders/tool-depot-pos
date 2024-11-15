package com.tooldepot.pos.domain;

import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.Objects;

/**
 * Falls on the same day of the week and the same appearance order in the month (e.g. 2nd Tue of August).
 */
@Getter
@ToString
@Slf4j
public class DayOfWeekAndMonthHoliday implements Holiday {
    private final Month month;
    private final DayOfWeek dayOfWeek;
    private final String name;
    private final AppearanceOrderInMonth appearanceOrderInMonth;

    public DayOfWeekAndMonthHoliday(String name, Month month, DayOfWeek dayOfWeek,
                                    AppearanceOrderInMonth appearanceOrderInMonth) {
        Objects.requireNonNull(name, "name is required");
        Objects.requireNonNull(month, "month is required");
        Objects.requireNonNull(dayOfWeek, "dayOfWeek is required");
        Objects.requireNonNull(appearanceOrderInMonth, "appearanceOrderInMonth is required");

        this.name = name;
        this.month = month;
        this.dayOfWeek = dayOfWeek;
        this.appearanceOrderInMonth = appearanceOrderInMonth;
    }

    @Override
    public boolean isObservedHolidayDate(LocalDate date) {
        Objects.requireNonNull(date, "date is required");
        return date.equals(calculateObservedDate(date.getYear()));
    }

    protected LocalDate calculateObservedDate(int year) {
        if(appearanceOrderInMonth == AppearanceOrderInMonth.LAST) {
            return calculateLastObservedDateInMonth(year);
        } else {
            return calculateFirstThruFourthAppearanceOrderInMonth(year);
        }
    }

    private LocalDate calculateLastObservedDateInMonth(int year) {
        LocalDate trialDate = LocalDate.of(year, month, 1).plusMonths(1).minusDays(1);
        log.debug("initial last trialDate={}, dayOfWeek={}", trialDate, trialDate.getDayOfWeek());
        while (trialDate.getDayOfWeek() != dayOfWeek) {
            trialDate = trialDate.minusDays(1);
        }
        log.debug("found last observedDate={}, dayOfWeek={}, week={}", trialDate, trialDate.getDayOfWeek(), "LAST");
        return trialDate;
    }

    private LocalDate calculateFirstThruFourthAppearanceOrderInMonth(int year) {
        LocalDate trialDate = LocalDate.of(year, month, 1);
        log.debug("initial trialDate={}, dayOfWeek={}", trialDate, trialDate.getDayOfWeek());
        while (trialDate.getDayOfWeek() != dayOfWeek) {
            trialDate = trialDate.plusDays(1);
        }

        log.debug("first trialDate={}, dayOfWeek={}", trialDate, trialDate.getDayOfWeek());
        int currentWeekNumber = 1;
        do {
            if (currentWeekNumber == appearanceOrderInMonth.getWeekNumber()) {
                log.debug("found observedDate={}, dayOfWeek={}, weekNumber={}", trialDate, trialDate.getDayOfWeek(), currentWeekNumber);
                return trialDate;
            } else {
                trialDate = trialDate.plusWeeks(1);
                currentWeekNumber++;
            }
        } while (currentWeekNumber <= 5);

        // shouldn't ever get here
        throw new IllegalStateException("Could not find the " + appearanceOrderInMonth + " " + dayOfWeek + " in " + month);
    }


}
