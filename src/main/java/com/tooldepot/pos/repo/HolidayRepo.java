package com.tooldepot.pos.repo;

import com.tooldepot.pos.domain.DayOfWeekHoliday;
import com.tooldepot.pos.domain.ExactDayHoliday;
import com.tooldepot.pos.domain.Holiday;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.tooldepot.pos.domain.Holiday.AppearanceModifier.CLOSEST_WEEKDAY;
import static com.tooldepot.pos.domain.Holiday.AppearanceOrderInMonth.FIRST;

@Service
public class HolidayRepo {
    public List<Holiday> holidays = new ArrayList<>();

    public HolidayRepo() {
        holidays.add(new ExactDayHoliday("Independence Day", Month.JULY, 4, CLOSEST_WEEKDAY));
        holidays.add(new DayOfWeekHoliday("Labor Day", Month.SEPTEMBER, DayOfWeek.MONDAY, FIRST));
    }

    public List<Holiday> findAllHolidays() {
        return Collections.unmodifiableList(holidays);
    }
}
