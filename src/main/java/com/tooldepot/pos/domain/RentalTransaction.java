package com.tooldepot.pos.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RentalTransaction(
        Tool tool,
        int rentalDays,
        LocalDate checkoutDate,
        LocalDate dueDate,
        BigDecimal dailyRentalCharge,
        int chargeDays,
        BigDecimal preDiscountCharge,
        int discountPercent,
        BigDecimal discountAmount,
        BigDecimal finalCharge
) {
}
