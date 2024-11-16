package com.tooldepot.pos.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tooldepot.pos.api.RentalApiController.CheckoutRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.hamcrest.Matchers.equalTo;
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
    public void testCheckout() throws Exception {
        log.info("testCheckout");

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

//    @Test
//    public void testGetTool_unknownCode() throws Exception{
//        log.info("testGetTool_unknownCode");
//
//        mockMvc.perform(get("/api/tools/ZZZZ")
//                .contentType("application/json"))
//                .andExpect(status().isNotFound());
//    }
}
