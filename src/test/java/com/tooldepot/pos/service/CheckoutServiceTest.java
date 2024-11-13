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

        checkoutWithException("LADW", 0, 0, LocalDate.now(), PosServiceException.Error.INVALID_RENTAL_DAYS);
        checkoutWithException("LADW", -1, 0, LocalDate.now(), PosServiceException.Error.INVALID_RENTAL_DAYS);
        checkoutWithException("LADW", -999, 0, LocalDate.now(), PosServiceException.Error.INVALID_RENTAL_DAYS);
    }

    private void checkoutWithException(String toolCode, int rentalDays, int discount,
                                       LocalDate checkoutDate, PosServiceException.Error expectedError) {
        try {
            checkoutService.checkout(toolCode, rentalDays, discount, checkoutDate);
            fail("Should not have thrown PosServiceException");
        } catch (PosServiceException e) {
            assertEquals(expectedError, e.getErrorCode());
        }
    }

    @Test
    public void testValidCheckout_invalidToolCode() {
        log.info("Testing checkout invalid tool code");
        checkoutWithException("ABCD", 1, 0, LocalDate.now(), PosServiceException.Error.INVALID_TOOL_CODE);
    }

    @Test
    public void testValidCheckout_invalidDiscount() {
        log.info("Testing checkout invalid discount");

        checkoutWithException("JAKR", 2, -1, LocalDate.now(), PosServiceException.Error.INVALID_DISCOUNT_PERCENTAGE);
        checkoutWithException("JAKR", 2, 101, LocalDate.now(), PosServiceException.Error.INVALID_DISCOUNT_PERCENTAGE);
        checkoutWithException("JAKR", 2, -999, LocalDate.now(), PosServiceException.Error.INVALID_DISCOUNT_PERCENTAGE);
        checkoutWithException("JAKR", 2, 255, LocalDate.now(), PosServiceException.Error.INVALID_DISCOUNT_PERCENTAGE);
    }
}
