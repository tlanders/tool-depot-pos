package com.tooldepot.pos.service;

import com.tooldepot.pos.domain.Tool;
import com.tooldepot.pos.domain.ToolType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Slf4j
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

    @Test
    public void testGetTool() {
        Optional<Tool> tool = toolService.getTool("LADW");
        assertThat(tool)
                .isPresent()
                .get()
                .extracting(Tool::toolType)
                .isEqualTo(ToolType.LADDER);
    }

    @Test
    public void testGetTool_invalidToolCode() {
        Optional<Tool> tool = toolService.getTool("ABCD");
        assertThat(tool)
                .isEmpty();
    }
}
