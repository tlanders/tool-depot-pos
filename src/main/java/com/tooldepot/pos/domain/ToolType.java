package com.tooldepot.pos.domain;

import lombok.Getter;

import java.math.BigDecimal;

import static com.tooldepot.pos.util.BigDecimalUtil.newBD;

@Getter
public enum ToolType {
    LADDER("Ladder", newBD("1.99"), true, true, false),
    CHAINSAW("Chainsaw", newBD("1.49"), true, false, true),
    JACKHAMMER("Jackhammer", newBD("2.99"), true, false, false);

    private final String description;
    private final BigDecimal dailyCharge;
    private final boolean weekdayCharge;
    private final boolean weekendCharge;
    private final boolean holidayCharge;

    ToolType(String description, BigDecimal dailyCharge, boolean weekdayCharge, boolean weekendCharge, boolean holidayCharge) {
        this.description = description;
        this.dailyCharge = dailyCharge;
        this.weekdayCharge = weekdayCharge;
        this.weekendCharge = weekendCharge;
        this.holidayCharge = holidayCharge;
    }
}
