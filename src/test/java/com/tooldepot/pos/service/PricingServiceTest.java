package com.tooldepot.pos.service;

import com.tooldepot.pos.domain.RentalCharge;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class PricingServiceTest {
    @Autowired
    private PricingService pricingService;

    @Test
    public void testCalculateRentalCharges() {
        testRentalCharges(new BigDecimal("5.97"), new BigDecimal("5.97"), new BigDecimal("1.99"), 3, 3, 0);
    }

    private void testRentalCharges(BigDecimal expectedPreDiscountCharge, BigDecimal expectedFinalCharge,
                                          BigDecimal rentalRate, int daysRented, int chargeDays, int discountPercent) {
        RentalCharge rentalCharge = pricingService.calculateRentalCharges(new BigDecimal("1.99"), 3, 3, 0);

        assertEquals(new BigDecimal("5.97"), rentalCharge.preDiscountCharge());
        assertEquals(new BigDecimal("5.97"), rentalCharge.finalCharge());
    }
}
