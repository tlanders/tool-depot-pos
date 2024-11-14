package com.tooldepot.pos.domain;

import java.time.LocalDate;

@FunctionalInterface
public interface Holiday {
    boolean isHoliday(LocalDate date);

    enum AppearanceModifier {
        NONE,
        CLOSEST_WEEKDAY
    }

    enum AppearanceOrderInMonth {
        FIRST,
        SECOND,
        THIRD,
        FOURTH,
        LAST
    }
}
