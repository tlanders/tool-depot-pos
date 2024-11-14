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
        log.info("getRentalPeriod(tool={}, checkoutDate={}, rentalDays={})", tool, checkoutDate, rentalDays);

        Set<LocalDate> noChargeDays = new TreeSet<>();
        for(int i = 0; i < rentalDays; i++) {
            LocalDate date = checkoutDate.plusDays(i);
            if(isWeekday(date) && !tool.weekdayCharge()) {
                noChargeDays.add(date);
            } else if(isWeekend(date) && !tool.weekendCharge()) {
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
