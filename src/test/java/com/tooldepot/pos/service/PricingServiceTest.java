package com.tooldepot.pos.service;

import com.tooldepot.pos.domain.RentalCharge;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static com.tooldepot.pos.util.BigDecimalUtil.newBD;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class PricingServiceTest {
    @Autowired
    private PricingService pricingService;

    @Test
    public void testCalculateRentalCharges() {
        testRentalCharges(newBD("5.97"), newBD("0.00"), newBD("5.97"), newBD("1.99"), 3, 3, 0);
        testRentalCharges(newBD("5.97"), newBD("0.60"), newBD("5.37"), newBD("1.99"), 3, 3, 10);
//        testRentalCharges(newBD("3.98"), newBD("0.00"), newBD("3.98"), newBD("1.99"), 3, 2, 0);
    }

    private void testRentalCharges(BigDecimal expectedPreDiscountCharge, BigDecimal expectedDiscountAmount, BigDecimal expectedFinalCharge,
                                          BigDecimal rentalRate, int daysRented, int chargeDays, int discountPercent) {
        RentalCharge rentalCharge = pricingService.calculateRentalCharges(rentalRate, daysRented, chargeDays, discountPercent);

        assertEquals(expectedPreDiscountCharge, rentalCharge.preDiscountCharge());
        assertEquals(expectedDiscountAmount, rentalCharge.discountAmount());
        assertEquals(expectedFinalCharge, rentalCharge.finalCharge());
    }

    @Test
    public void testCalculateDiscountMultiplier() {
        assertEquals(newBD("0.00"), pricingService.calculateDiscountMultiplier(0));
        assertEquals(newBD("1.00"), pricingService.calculateDiscountMultiplier(100));
        assertEquals(newBD("0.50"), pricingService.calculateDiscountMultiplier(50));
        assertEquals(newBD("0.33"), pricingService.calculateDiscountMultiplier(33));
        assertEquals(newBD("0.75"), pricingService.calculateDiscountMultiplier(75));
    }
}
