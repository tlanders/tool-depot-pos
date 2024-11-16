package com.tooldepot.pos.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tooldepot.pos.api.RentalApiController.CheckoutRequest;
import com.tooldepot.pos.service.PosServiceException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
public class RentalApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testValidCheckout() throws Exception {
        log.info("testValidCheckout");

        CheckoutRequest request = new CheckoutRequest("LADW", 3, 0,
                LocalDate.of(2024, 11, 1));

        mockMvc.perform(post("/api/rentals")
                        .content(objectMapper.writeValueAsString(request))
                .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tool.toolCode", equalTo(request.toolCode())))
                .andExpect(jsonPath("$.checkoutDate", equalTo("11/01/24")));
    }

    @Test
    public void testCheckout_invalidRentalDays() throws Exception {
        log.info("testCheckout_invalidRentalDays");

        testInvalidCheckout("LADW", -3, 0,
                LocalDate.of(2024, 11, 1),
                PosServiceException.Error.INVALID_RENTAL_DAYS.getErrorCode());
        testInvalidCheckout("LADW", 0, 0,
                LocalDate.of(2024, 11, 1),
                PosServiceException.Error.INVALID_RENTAL_DAYS.getErrorCode());
    }

    @Test
    public void testCheckout_invalidToolCode() throws Exception {
        log.info("testCheckout_invalidToolCode");

        testInvalidCheckout("ABC", 4, 0,
                LocalDate.of(2024, 11, 1),
                PosServiceException.Error.INVALID_TOOL_CODE.getErrorCode());
        testInvalidCheckout("", 5, 0,
                LocalDate.of(2024, 11, 1),
                PosServiceException.Error.INVALID_TOOL_CODE.getErrorCode());
    }

    @Test
    public void testCheckout_invalidDiscount() throws Exception {
        log.info("testCheckout_invalidDiscount");

        testInvalidCheckout("LADW", 4, -1,
                LocalDate.of(2024, 11, 1),
                PosServiceException.Error.INVALID_DISCOUNT_PERCENTAGE.getErrorCode());
        testInvalidCheckout("LADW", 5, 101,
                LocalDate.of(2024, 11, 1),
                PosServiceException.Error.INVALID_DISCOUNT_PERCENTAGE.getErrorCode());
    }

    private void testInvalidCheckout(String toolCode, int rentalDays, int discountPercent,
                                     LocalDate checkoutDate, int expectedErrorCode) throws Exception {
        CheckoutRequest request = new CheckoutRequest(toolCode, rentalDays, discountPercent,
                checkoutDate);

        mockMvc.perform(post("/api/rentals")
                        .content(objectMapper.writeValueAsString(request))
                .contentType("application/json"))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.resultCode", equalTo(expectedErrorCode)))
                .andExpect(jsonPath("$.message", not(emptyOrNullString())));
    }
}
