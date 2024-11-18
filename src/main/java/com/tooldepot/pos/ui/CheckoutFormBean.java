package com.tooldepot.pos.ui;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
public class CheckoutFormBean {
    @NotEmpty(message = "Please enter a valid 4-character tool code")
    @Pattern(regexp = "^[A-Za-z]{4}$", message = "Please enter a valid 4-character tool code")
    private String toolCode;

    @Min(value = 1, message = "Rental days must be at least 1")
    private int rentalDays = 1;

    @Min(value = 0, message = "Discount percent must be at least 0 and not more than 100")
    @Max(value = 100, message = "Discount percent must be at least 0 and not more than 100")
    private int discountPercent;

    @NotEmpty(message = "Please enter a valid checkout date (MM/dd/yy)")
    @Pattern(regexp = "^\\d\\d/\\d\\d/\\d\\d$", message = "Please enter a valid checkout date (MM/dd/yy)")
    private String checkoutDate;
}
