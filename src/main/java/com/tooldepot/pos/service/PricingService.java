package com.tooldepot.pos.service;

import com.tooldepot.pos.domain.RentalCharge;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Objects;

import static com.tooldepot.pos.util.BigDecimalUtil.*;

/**
 * Service to calculate rental charges for a tool rental.
 */
@Service
@Slf4j
public class PricingService {

    public RentalCharge calculateRentalCharges(BigDecimal dailyRate, int chargeDays, int discountPercent)
            throws PosServiceException
    {
        Objects.requireNonNull(dailyRate, "dailyRate is required");
        if(discountPercent < 0 || discountPercent > 100) {
            throw new PosServiceException(PosServiceException.Error.INVALID_DISCOUNT_PERCENT,
                    "Discount percent must be between 0 and 100");
        }
        if(chargeDays < 0) {
            throw new IllegalArgumentException("chargeDays must be >= 0");
        }
        if(dailyRate.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("dailyRate must be >= 0");
        }

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
