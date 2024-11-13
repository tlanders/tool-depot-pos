package com.tooldepot.pos.repo;

import com.tooldepot.pos.domain.Tool;
import com.tooldepot.pos.domain.ToolType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class ToolRepo {
    private final Map<String, Tool> tools = new HashMap<>();

    public ToolRepo() {
        tools.put("CHNS", new Tool("CHNS", ToolType.CHAINSAW, "Stihl", new BigDecimal("1.49"), true, false, true));
        tools.put("LADW", new Tool("LADW", ToolType.LADDER, "Werner", new BigDecimal("1.99"), true, true, false));
        tools.put("JAKD", new Tool("JAKD", ToolType.JACKHAMMER, "DeWalt", new BigDecimal("2.99"), true, false, false));
        tools.put("JAKR", new Tool("JAKR", ToolType.JACKHAMMER, "Ridgid", new BigDecimal("2.99"), true, false, false));
    }

    public List<Tool> findAllTools() {
        return new ArrayList<>(tools.values());
    }

    public Optional<Tool> getTool(String toolCode) {
        Tool tool = tools.get(toolCode);
        return Optional.ofNullable(tool);
    }
}
