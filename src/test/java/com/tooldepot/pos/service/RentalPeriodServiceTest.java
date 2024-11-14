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
        Tool toolAllDaysCharged = new Tool("LADW", ToolType.LADDER, "Werner",
                newBD("1.99"), true, true, true);
        Tool toolNoWeekdayCharge = new Tool("LADW", ToolType.LADDER, "Werner",
                newBD("1.99"), false, true, true);
        Tool toolNoWeekendCharge = new Tool("LADW", ToolType.LADDER, "Werner",
                newBD("1.99"), true, false, true);
        Tool toolNoHolidayCharge = new Tool("LADW", ToolType.LADDER, "Werner",
                newBD("1.99"), true, true, false);
        Tool toolNoCharges = new Tool("LADW", ToolType.LADDER, "Werner",
                newBD("1.99"), false, false, false);

        testRentalPeriodCalculations(3, toolAllDaysCharged, LocalDate.of(2024, 11, 11), 3);   // Mon, 3 days, no holidays
        testRentalPeriodCalculations(4, toolAllDaysCharged, LocalDate.of(2024, 11, 16), 4);   // Sat, 4 days, no holidays
    }

    private void testRentalPeriodCalculations(int expectedChargeDays, Tool testTool, LocalDate checkoutDate, int rentalDays) {
        RentalPeriod period = rentalPeriodService.getRentalPeriod(testTool, checkoutDate, rentalDays);

        assertAll("rentalPeriod",
                () -> assertEquals(expectedChargeDays, period.chargeDays()),
                () -> assertEquals(rentalDays, period.rentalDays()),
                () -> assertEquals(checkoutDate, period.checkoutDate()),
                () -> assertEquals(checkoutDate.plusDays(rentalDays), period.returnDate())
        );
    }
}