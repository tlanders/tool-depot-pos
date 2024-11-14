package com.tooldepot.pos.service;

import com.tooldepot.pos.domain.RentalPeriod;
import com.tooldepot.pos.domain.Tool;
import com.tooldepot.pos.domain.ToolType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static com.tooldepot.pos.util.BigDecimalUtil.newBD;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class RentalPeriodServiceTest {
    @Autowired
    private RentalPeriodService rentalPeriodService;

    @Test
    public void testGetRentalPeriod() {
        Tool testTool = new Tool("LADW", ToolType.LADDER, "Werner",
                newBD("1.99"), true, true, true);

        testRentalPeriodCalculations(testTool, LocalDate.of(2024, 11, 9), 3);   // Mon, 3 days, no weekend, no holiday
    }

    private void testRentalPeriodCalculations(Tool testTool, LocalDate checkoutDate, int rentalDays) {
        RentalPeriod period = rentalPeriodService.getRentalPeriod(testTool, checkoutDate, rentalDays);

        assertAll("rentalPeriod",
                () -> assertEquals(rentalDays, period.rentalDays()),
                () -> assertEquals(checkoutDate, period.checkoutDate()),
                () -> assertEquals(checkoutDate.plusDays(rentalDays), period.returnDate()),
                () -> assertEquals(rentalDays, period.chargeDays())
        );
    }
}