package com.tooldepot.pos.service;

import com.tooldepot.pos.domain.RentalCharge;
import com.tooldepot.pos.domain.RentalTransaction;
import com.tooldepot.pos.domain.Tool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
public class CheckoutService {
    @Autowired
    private ToolService toolService;

    @Autowired
    private PricingService pricingService;

    public RentalTransaction checkout(String toolCode, int rentalDays,
                                      int discountPercent, LocalDate checkoutDate
    ) throws PosServiceException {
        log.debug("checkout service called - tool={}, rentalDays={}, discountPercent={}, checkoutDate={}",
                toolCode, rentalDays, discountPercent, checkoutDate);

        if(rentalDays <= 0) {
            throw new PosServiceException(PosServiceException.Error.INVALID_RENTAL_DAYS, "Rental days must be greater than 0");
        }
        if(discountPercent < 0 || discountPercent > 100) {
            throw new PosServiceException(PosServiceException.Error.INVALID_DISCOUNT_PERCENTAGE,
                    "Discount percent must be between 0 and 100");
        }

        // TODO - add validation for checkout date

        Tool tool = toolService.getTool(toolCode)
                .orElseThrow(() -> new PosServiceException(PosServiceException.Error.INVALID_TOOL_CODE,
                        "Tool " + toolCode + " not found"));

        RentalCharge rentalCharge = pricingService.calculateRentalCharges(tool.toolType().getDailyCharge(), rentalDays, discountPercent);

        return new RentalTransaction(
                tool,
                rentalDays,
                checkoutDate,
                checkoutDate.plusDays(rentalDays),
                tool.toolType().getDailyCharge(),
                rentalDays,
                rentalCharge.preDiscountCharge(),
                discountPercent,
                rentalCharge.discountAmount(),
                rentalCharge.preDiscountCharge().subtract(rentalCharge.discountAmount()));
    }
}
