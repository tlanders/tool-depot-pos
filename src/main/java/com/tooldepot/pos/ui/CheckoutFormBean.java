package com.tooldepot.pos.ui;

import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Data
public class CheckoutFormBean {
    @NotEmpty(message = "Tool code is required")
    private String toolCode;

    @NotEmpty(message = "Rental days is required")
    private String rentalDays = "1";

    @NotEmpty(message = "Discount percent is required")
    private String discountPercent = "0";

    @NotEmpty(message = "Checkout date is required")
    private String checkoutDate;
}
