package com.tooldepot.pos.service;

import com.tooldepot.pos.domain.Tool;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class ToolServiceTest {
    @Autowired
    private ToolService toolService;

    @Test
    public void testFindAllTools() {
        List<Tool> tools = toolService.findAllTools();
        assertThat(tools)
                .isNotNull()
                .hasSizeGreaterThan(0);
    }
}
