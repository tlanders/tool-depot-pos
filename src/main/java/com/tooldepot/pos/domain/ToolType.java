package com.tooldepot.pos.domain;

import lombok.Getter;

@Getter
public enum ToolType {
    LADDER("Ladder"),
    CHAINSAW("Chainsaw"),
    JACKHAMMER("Jackhammer");

    private final String description;

    ToolType(String description) {
        this.description = description;
    }
}
