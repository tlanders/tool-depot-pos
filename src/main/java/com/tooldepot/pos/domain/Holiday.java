package com.tooldepot.pos.domain;

import lombok.Getter;

import java.time.LocalDate;

@FunctionalInterface
public interface Holiday {
    boolean isObservedHolidayDate(LocalDate date);

    enum AppearanceModifier {
        NONE,
        CLOSEST_WEEKDAY
    }

    @Getter
    enum AppearanceOrderInMonth {
        FIRST(1),
        SECOND(2),
        THIRD(3),
        FOURTH(4),
        LAST(-1);

        private final int weekNumber;

        AppearanceOrderInMonth(int weekNumber) {
            this.weekNumber = weekNumber;
        }
    }
}
