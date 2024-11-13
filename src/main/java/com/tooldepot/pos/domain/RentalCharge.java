package com.tooldepot.pos.domain;

import java.math.BigDecimal;

public record RentalCharge(
        BigDecimal preDiscountCharge,
        BigDecimal discountAmount,
        BigDecimal finalCharge
) {
}
