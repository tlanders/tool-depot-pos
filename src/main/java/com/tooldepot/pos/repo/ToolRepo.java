package com.tooldepot.pos.repo;

import com.tooldepot.pos.domain.Tool;
import com.tooldepot.pos.domain.ToolType;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class ToolRepo {
    private final List<Tool> tools = new ArrayList<>();

    public ToolRepo() {
        tools.add(new Tool("CHNS", ToolType.CHAINSAW, "Stihl", new BigDecimal("1.49"), true, false, true));
        tools.add(new Tool("LADW", ToolType.LADDER, "Werner", new BigDecimal("1.99"), true, true, false));
        tools.add(new Tool("JAKD", ToolType.JACKHAMMER, "DeWalt", new BigDecimal("2.99"), true, false, false));
        tools.add(new Tool("JAKR", ToolType.JACKHAMMER, "Ridgid", new BigDecimal("2.99"), true, false, false));
    }

    public List<Tool> findAllTools() {
        return Collections.unmodifiableList(tools);
    }
}
