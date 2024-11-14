package com.tooldepot.pos.service;

import com.tooldepot.pos.domain.RentalPeriod;
import com.tooldepot.pos.domain.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

@Service
@Slf4j
public class RentalPeriodService {
    public RentalPeriod getRentalPeriod(Tool tool, LocalDate checkoutDate, int rentalDays) {
        log.debug("getRentalPeriod(tool={}, checkoutDate={}, rentalDays={})", tool, checkoutDate, rentalDays);
        return getRentalPeriod(checkoutDate, rentalDays, tool.toolType().isWeekdayCharge(),
                tool.toolType().isWeekendCharge(), tool.toolType().isHolidayCharge());
    }

    protected RentalPeriod getRentalPeriod(LocalDate checkoutDate, int rentalDays, boolean isWeekdayCharge,
                                           boolean isWeekendCharge, boolean isHolidayCharge) {
        log.debug("getRentalPeriod - checkoutDate={}, rentalDays={}, weekdayCharge={}, weekendCharge={}, holidayCharge={}",
                checkoutDate, rentalDays, isWeekdayCharge, isWeekendCharge, isHolidayCharge);

        Set<LocalDate> noChargeDays = new TreeSet<>();
        for(int i = 0; i < rentalDays; i++) {
            LocalDate date = checkoutDate.plusDays(i);
            if(isWeekday(date) && !isWeekdayCharge) {
                noChargeDays.add(date);
            } else if(isWeekend(date) && !isWeekendCharge) {
                noChargeDays.add(date);
            }
        }

        return new RentalPeriod(rentalDays, rentalDays - noChargeDays.size(), checkoutDate, checkoutDate.plusDays(rentalDays));
    }

    private static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private static boolean isWeekday(LocalDate date) {
        return !isWeekend(date);
    }
}
