package com.tooldepot.pos.service;

import com.tooldepot.pos.domain.RentalTransaction;
import com.tooldepot.pos.domain.Tool;
import com.tooldepot.pos.domain.ToolType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static com.tooldepot.pos.util.BigDecimalUtil.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class CheckoutServiceTest {
    @Autowired
    private CheckoutService checkoutService;

    @Test
    public void testValidCheckout() throws PosServiceException {
        log.info("Testing valid checkout");

        Tool testTool = new Tool("LADW", ToolType.LADDER, "Werner");

        testValidCheckout(testTool, 3, 0, LocalDate.now());

        checkoutService.checkout("LADW", 1, 0, LocalDate.now());
        checkoutService.checkout("LADW", 3, 10, LocalDate.now());
        checkoutService.checkout("LADW", 7, 53, LocalDate.now());
    }

    private void testValidCheckout(Tool testTool, int rentalDays, int discountPercent, LocalDate checkoutDate) throws PosServiceException {
        RentalTransaction rental = checkoutService.checkout(testTool.toolCode(), rentalDays, discountPercent, checkoutDate);
        assertAll("rental",
                () -> assertNotNull(rental),
                () -> assertNotNull(rental.tool()),
                () -> assertEquals(testTool.toolCode(), rental.tool().toolCode()),
                () -> assertEquals(testTool.toolType(), rental.tool().toolType()),
                () -> assertEquals(testTool.brand(), rental.tool().brand()),
                () -> assertEquals(rentalDays, rental.rentalDays()),
                () -> assertEquals(checkoutDate, rental.checkoutDate()),
                () -> assertEquals(checkoutDate.plusDays(rentalDays), rental.dueDate()),
                () -> assertEquals(testTool.toolType().getDailyCharge(), rental.dailyRentalCharge()),
                () -> assertEquals(rentalDays, rental.chargeDays()),
                () -> assertEquals(multiply(testTool.toolType().getDailyCharge(), rentalDays, 2), rental.preDiscountCharge()),
                () -> assertEquals(discountPercent, rental.discountPercent())
        );
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
            fail("Should have thrown PosServiceException");
        } catch (PosServiceException e) {
            assertEquals(expectedError, e.getErrorCode(), "Error code should be " + expectedError);
        }
    }

    @Test
    public void testCheckout_invalidToolCode() {
        log.info("Testing checkout with invalid tool code");
        checkoutWithException("ABCD", 1, 0, LocalDate.now(), PosServiceException.Error.INVALID_TOOL_CODE);
    }

    @Test
    public void testCheckout_invalidDiscount() {
        log.info("Testing checkout with invalid discount");

        checkoutWithException("JAKR", 2, -1, LocalDate.now(), PosServiceException.Error.INVALID_DISCOUNT_PERCENTAGE);
        checkoutWithException("JAKR", 2, 101, LocalDate.now(), PosServiceException.Error.INVALID_DISCOUNT_PERCENTAGE);
        checkoutWithException("JAKR", 2, -999, LocalDate.now(), PosServiceException.Error.INVALID_DISCOUNT_PERCENTAGE);
        checkoutWithException("JAKR", 2, 255, LocalDate.now(), PosServiceException.Error.INVALID_DISCOUNT_PERCENTAGE);
    }
}
