package com.tooldepot.pos.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class PricingService {
    public BigDecimal calculateRentalCharges(BigDecimal dailyRate, int daysRented) {
        log.debug("calculateRentalCharge dailyRate={}, daysRented={}", dailyRate, daysRented);
        return dailyRate.multiply(BigDecimal.valueOf(daysRented));
    }
}
