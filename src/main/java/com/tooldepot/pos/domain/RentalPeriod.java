package com.tooldepot.pos.domain;

import java.time.LocalDate;

public record RentalPeriod(int rentalDays, int chargeDays, LocalDate checkoutDate, LocalDate returnDate) {
}
