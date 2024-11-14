package com.tooldepot.pos.service;

import com.tooldepot.pos.domain.RentalPeriod;
import com.tooldepot.pos.domain.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@Slf4j
public class RentalPeriodService {
    public RentalPeriod getRentalPeriod(Tool tool, LocalDate checkoutDate, int rentalDays) {
        log.info("getRentalPeriod(tool={}, checkoutDate={}, rentalDays={})", tool, checkoutDate, rentalDays);
        return new RentalPeriod(rentalDays, rentalDays, checkoutDate, checkoutDate.plusDays(rentalDays));
    }
}
