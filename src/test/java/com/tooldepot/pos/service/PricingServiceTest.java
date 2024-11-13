package com.tooldepot.pos.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class PricingServiceTest {
    @Test
    public void testCalculateRentalCharges() {
        PricingService service = new PricingService();
        BigDecimal result = service.calculateRentalCharges(new BigDecimal("1.99"), 3);

        assertEquals(new BigDecimal("5.97"), result);
    }
}
