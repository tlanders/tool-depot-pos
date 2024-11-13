package com.tooldepot.pos.service;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
@Slf4j
public class CheckoutServiceTest {
    @Autowired
    private CheckoutService checkoutService;


    @Test
    public void testValidCheckout() {
        log.info("Testing valid checkout");

        checkoutService.checkout("LADW", 3, 0, LocalDate.now());
    }
}
