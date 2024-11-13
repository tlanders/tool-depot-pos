package com.tooldepot.pos.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
public class CheckoutService {
    public void checkout(String toolCode, int rentalDays, int discountPercent, LocalDate checkoutDate) {
        log.debug("Checkout service called - tool={}, rentalDays={}, discountPercent={}, checkoutDate={}",
                toolCode, rentalDays, discountPercent, checkoutDate);
    }
}
