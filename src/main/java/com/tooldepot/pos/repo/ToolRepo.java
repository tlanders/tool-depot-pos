package com.tooldepot.pos.repo;

import com.tooldepot.pos.domain.Tool;
import com.tooldepot.pos.domain.ToolType;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Demo repo to be replaced by a real db.
 */
@Service
public class ToolRepo {
    private final Map<String, Tool> tools = new HashMap<>();

    public ToolRepo() {
        tools.put("CHNS", new Tool("CHNS", ToolType.Chainsaw, "Stihl"));
        tools.put("LADW", new Tool("LADW", ToolType.Ladder, "Werner"));
        tools.put("JAKD", new Tool("JAKD", ToolType.Jackhammer, "DeWalt"));
        tools.put("JAKR", new Tool("JAKR", ToolType.Jackhammer, "Ridgid"));
    }

    public List<Tool> findAllTools() {
        return new ArrayList<>(tools.values());
    }

    public Optional<Tool> getTool(String toolCode) {
        Tool tool = tools.get(toolCode);
        return Optional.ofNullable(tool);
    }
}
