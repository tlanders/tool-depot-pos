package com.tooldepot.pos.service;

import com.tooldepot.pos.domain.RentalCharge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

import static com.tooldepot.pos.util.BigDecimalUtil.*;

@Service
@Slf4j
public class PricingService {
    private static final BigDecimal BD_100 = newBD(100);

    public RentalCharge calculateRentalCharges(BigDecimal dailyRate, int daysRented, int chargeDays, int discountPercent) {
        Objects.requireNonNull(dailyRate, "dailyRate is required");

        log.debug("calculateRentalCharge dailyRate={}, daysRented={}", dailyRate, daysRented);

        BigDecimal preDiscountCharge = dailyRate.multiply(BigDecimal.valueOf(daysRented)).setScale(2, RoundingMode.HALF_UP);
        BigDecimal discountAmount = preDiscountCharge.multiply(calculateDiscountMultiplier(discountPercent))
                .setScale(2, RoundingMode.HALF_UP);
        BigDecimal finalCharge = preDiscountCharge.subtract(discountAmount);

        return new RentalCharge(preDiscountCharge, discountAmount, finalCharge);
    }

    protected BigDecimal calculateDiscountMultiplier(int discountPercent) {
        return newBD(discountPercent).divide(BD_100, 2, RoundingMode.HALF_UP);
    }
}
