package com.tooldepot.pos.service;

import com.tooldepot.pos.domain.RentalCharge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

import static com.tooldepot.pos.util.BigDecimalUtil.*;

@Service
@Slf4j
public class PricingService {

    public RentalCharge calculateRentalCharges(BigDecimal dailyRate, int chargeDays, int discountPercent) {
        Objects.requireNonNull(dailyRate, "dailyRate is required");

        log.debug("calculateRentalCharge dailyRate={}, chargeDays={}, discountPercent={}", dailyRate, chargeDays, discountPercent);

        BigDecimal preDiscountCharge = multiply(dailyRate, chargeDays, 2);
        BigDecimal discountAmount = multiply(preDiscountCharge, calculateDiscountMultiplier(discountPercent), 2);
        BigDecimal finalCharge = preDiscountCharge.subtract(discountAmount);

        return new RentalCharge(preDiscountCharge, discountAmount, finalCharge);
    }

    protected BigDecimal calculateDiscountMultiplier(int discountPercent) {
        return divide(discountPercent, BD_100, 2);
    }
}
