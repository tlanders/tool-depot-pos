package com.tooldepot.pos.domain;

import java.math.BigDecimal;

public record Tool(
        String toolCode,
        ToolType toolType,
        String brand,
        BigDecimal dailyCharge,
        boolean weekdayCharge,
        boolean weekendCharge,
        boolean holidayCharge
) {
}
