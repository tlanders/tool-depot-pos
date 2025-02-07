package com.tooldepot.pos.api;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Slf4j
public class ToolApiControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindAllTools() throws Exception{
        log.info("testFindAllTools");

        mockMvc.perform(get("/api/tools")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.tools", hasSize(4)));
    }

    @Test
    public void testGetTool() throws Exception{
        log.info("testGetTool");

        mockMvc.perform(get("/api/tools/CHNS")
                .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.toolCode", equalTo("CHNS")));
    }

    @Test
    public void testGetTool_unknownCode() throws Exception{
        log.info("testGetTool_unknownCode");

        mockMvc.perform(get("/api/tools/ZZZZ")
                .contentType("application/json"))
                .andExpect(status().isNotFound());
    }
}
