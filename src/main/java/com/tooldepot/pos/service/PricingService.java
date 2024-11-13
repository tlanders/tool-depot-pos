package com.tooldepot.pos.service;

import com.tooldepot.pos.domain.RentalCharge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

@Service
@Slf4j
public class PricingService {
    public RentalCharge calculateRentalCharges(BigDecimal dailyRate, int daysRented, int chargeDays, int discountPercent) {
        Objects.requireNonNull(dailyRate, "dailyRate is required");

        log.debug("calculateRentalCharge dailyRate={}, daysRented={}", dailyRate, daysRented);

        BigDecimal preDiscountCharge = dailyRate.multiply(BigDecimal.valueOf(daysRented)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal finalCharge = dailyRate.multiply(BigDecimal.valueOf(daysRented)).setScale(2, RoundingMode.HALF_UP);

        return new RentalCharge(preDiscountCharge, BigDecimal.ZERO, finalCharge);
    }
}
