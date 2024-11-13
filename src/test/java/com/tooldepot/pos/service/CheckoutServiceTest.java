package com.tooldepot.pos.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class CheckoutServiceTest {
    @Autowired
    private CheckoutService checkoutService;

    @Test
    public void testValidCheckout() throws PosServiceException {
        log.info("Testing valid checkout");

        checkoutService.checkout("LADW", 3, 0, LocalDate.now());
        checkoutService.checkout("LADW", 1, 0, LocalDate.now());
        checkoutService.checkout("LADW", 3, 10, LocalDate.now());
        checkoutService.checkout("LADW", 7, 53, LocalDate.now());
    }

    @Test
    public void testValidCheckout_invalidRentalDays() {
        log.info("Testing checkout invalid rental days");

        assertThrows(PosServiceException.class, () -> {
            checkoutService.checkout("LADW", 0, 0, LocalDate.now());
        });

        assertThrows(PosServiceException.class, () -> {
            checkoutService.checkout("LADW", -1, 0, LocalDate.now());
        });
    }

    @Test
    public void testValidCheckout_invalidToolCode() {
        log.info("Testing checkout invalid tool code");
        assertThrows(PosServiceException.class, () -> {
            checkoutService.checkout("ABCD", 1, 0, LocalDate.now());
        });
    }

    @Test
    public void testValidCheckout_invalidDiscount() {
        log.info("Testing checkout invalid discount");

        assertThrows(PosServiceException.class, () -> {
            checkoutService.checkout("LADW", 2, -1, LocalDate.now());
        });

        assertThrows(PosServiceException.class, () -> {
            checkoutService.checkout("LADW", -1, 101, LocalDate.now());
        });
    }
}
