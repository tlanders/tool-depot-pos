package com.tooldepot.pos.domain;

public record Tool(
        String toolCode,
        ToolType toolType,
        String brand
) {
}
