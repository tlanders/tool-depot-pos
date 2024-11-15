package com.tooldepot.pos.service;

import com.tooldepot.pos.domain.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static com.tooldepot.pos.util.BigDecimalUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@Slf4j
public class CheckoutServiceTest {
    @Autowired
    private CheckoutService checkoutService;

    @MockBean
    private ToolService toolService;

    @MockBean
    private RentalPeriodService rentalPeriodService;

    @Test
    public void testValidCheckout() throws PosServiceException {
        log.info("Testing valid checkout");

        // Ladders don't charge on holidays
        testValidCheckout(new Tool("LADW", ToolType.LADDER, "Werner"),
                new RentalPeriod(3, 3,
                        LocalDate.of(2024, 11, 1),
                        LocalDate.of(2024, 11, 1).plusDays(3)),
                0);
        testValidCheckout(new Tool("LADW", ToolType.LADDER, "Werner"),
                new RentalPeriod(6, 5,
                        LocalDate.of(2024, 7, 1),
                        LocalDate.of(2024, 7, 1).plusDays(6)),
                        10);

        // Jackhammers don't charge on weekends or holidays
        testValidCheckout(new Tool("JCKB", ToolType.JACKHAMMER, "Jackhammer Brand"),
                new RentalPeriod(10, 7,
                        LocalDate.of(2024, 11, 20),
                        LocalDate.of(2024, 11, 20).plusDays(10)),
                        33);
        testValidCheckout(new Tool("JCKB", ToolType.JACKHAMMER, "Jackhammer Brand"),
                new RentalPeriod(10, 6,
                        LocalDate.of(2024, 11, 9),
                        LocalDate.of(2024, 11, 9).plusDays(10)),
                        23);
    }

    private void testValidCheckout(Tool expectedTool, RentalPeriod expectedRentalPeriod, int discountPercent) throws PosServiceException {
        when(toolService.getTool(any())).thenReturn(Optional.of(expectedTool));
        when(rentalPeriodService.getRentalPeriod(any(), any(), anyInt()))
                .thenReturn(expectedRentalPeriod);

        RentalTransaction rental = checkoutService.checkout(expectedTool.toolCode(), expectedRentalPeriod.rentalDays(),
                discountPercent, expectedRentalPeriod.checkoutDate());
        BigDecimal expectedPreDiscountCharge = multiply(expectedTool.toolType().getDailyCharge(), expectedRentalPeriod.chargeDays(), 2);
        BigDecimal expectedDiscountAmount = multiply(expectedPreDiscountCharge,
                divide(discountPercent, BD_100, 2), 2);
        BigDecimal expectedFinalCharge = expectedPreDiscountCharge.subtract(expectedDiscountAmount);

        assertAll("rental",
                () -> assertNotNull(rental),
                () -> assertNotNull(rental.tool()),
                () -> assertEquals(expectedTool.toolCode(), rental.tool().toolCode()),
                () -> assertEquals(expectedTool.toolType(), rental.tool().toolType()),
                () -> assertEquals(expectedTool.brand(), rental.tool().brand()),
                () -> assertEquals(expectedRentalPeriod.rentalDays(), rental.rentalDays()),
                () -> assertEquals(expectedRentalPeriod.checkoutDate(), rental.checkoutDate()),
                () -> assertEquals(expectedRentalPeriod.returnDate(), rental.dueDate()),
                () -> assertEquals(expectedTool.toolType().getDailyCharge(), rental.dailyRentalCharge()),
                () -> assertEquals(expectedRentalPeriod.chargeDays(), rental.chargeDays()),
                () -> assertEquals(expectedPreDiscountCharge, rental.preDiscountCharge()),
                () -> assertEquals(discountPercent, rental.discountPercent()),
                () -> assertEquals(expectedDiscountAmount, rental.discountAmount()),
                () -> assertEquals(expectedFinalCharge, rental.finalCharge())
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
    public void testCheckout_invalidRentaldays() {
        log.info("Testing checkout with invalid rental days");
        checkoutWithException("JAKR", 0, 0, LocalDate.now(), PosServiceException.Error.INVALID_RENTAL_DAYS);
        checkoutWithException("JAKR", -1, 10, LocalDate.now(), PosServiceException.Error.INVALID_RENTAL_DAYS);
        checkoutWithException("JAKR", -99, 20, LocalDate.now(), PosServiceException.Error.INVALID_RENTAL_DAYS);
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
