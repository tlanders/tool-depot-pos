package com.tooldepot.pos.service;

import com.tooldepot.pos.domain.Holiday;
import com.tooldepot.pos.domain.RentalPeriod;
import com.tooldepot.pos.domain.ToolType;
import com.tooldepot.pos.repo.HolidayRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.*;

/**
 * Service to calculate rental period and charge days for a tool.
 */
@Service
@Slf4j
public class RentalPeriodService {
    @Autowired
    private HolidayRepo holidayRepo;

    public RentalPeriod getRentalPeriod(ToolType toolType, LocalDate checkoutDate, int rentalDays) {
        log.debug("getRentalPeriod(toolType={}, checkoutDate={}, rentalDays={})", toolType, checkoutDate, rentalDays);
        return getRentalPeriod(checkoutDate, rentalDays, toolType.isWeekdayCharge(),
                toolType.isWeekendCharge(), toolType.isHolidayCharge());
    }

    protected RentalPeriod getRentalPeriod(LocalDate checkoutDate, int rentalDays, boolean isWeekdayCharge,
                                           boolean isWeekendCharge, boolean isHolidayCharge) {
        log.debug("getRentalPeriod - checkoutDate={}, rentalDays={}, weekdayCharge={}, weekendCharge={}, holidayCharge={}",
                checkoutDate, rentalDays, isWeekdayCharge, isWeekendCharge, isHolidayCharge);

        List<Holiday> holidays = holidayRepo.findAllHolidays();

        Set<LocalDate> noChargeDays = new TreeSet<>();
        for(int i = 0; i < rentalDays; i++) {
            LocalDate date = checkoutDate.plusDays(i);
            if((!isWeekdayCharge && isWeekday(date))
                    || (!isWeekendCharge && isWeekend(date))
                    || (!isHolidayCharge && isHoliday(holidays, date)))
            {
                noChargeDays.add(date);
            }
        }

        return new RentalPeriod(rentalDays, rentalDays - noChargeDays.size(), checkoutDate, checkoutDate.plusDays(rentalDays));
    }

    private static boolean isHoliday(List<Holiday> holidays, LocalDate date) {
        return holidays.stream().anyMatch(holiday -> holiday.isObservedHolidayDate(date));
    }

    private static boolean isWeekend(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.SATURDAY || date.getDayOfWeek() == DayOfWeek.SUNDAY;
    }

    private static boolean isWeekday(LocalDate date) {
        return !isWeekend(date);
    }
}
