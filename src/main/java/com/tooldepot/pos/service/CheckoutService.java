package com.tooldepot.pos.service;

import com.tooldepot.pos.domain.RentalCharge;
import com.tooldepot.pos.domain.RentalPeriod;
import com.tooldepot.pos.domain.RentalTransaction;
import com.tooldepot.pos.domain.Tool;
import com.tooldepot.pos.repo.RentalTransactionRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
 * Orchestrates the checkout process for a tool rental.
 */
@Slf4j
@Service
public class CheckoutService {
    private final ToolService toolService;
    private final RentalPeriodService rentalPeriodService;
    private final PricingService pricingService;
    private final RentalTransactionRepo rentalTransactionRepo;

    public CheckoutService(ToolService toolService, RentalPeriodService rentalPeriodService,
                           PricingService pricingService, RentalTransactionRepo rentalTransactionRepo) {
        this.toolService = toolService;
        this.rentalPeriodService = rentalPeriodService;
        this.pricingService = pricingService;
        this.rentalTransactionRepo = rentalTransactionRepo;
    }

    public RentalTransaction checkout(String toolCode, int rentalDays,
                                      int discountPercent, LocalDate checkoutDate
    ) throws PosServiceException {
        log.debug("checkout service called - tool={}, rentalDays={}, discountPercent={}, checkoutDate={}",
                toolCode, rentalDays, discountPercent, checkoutDate);

        if(rentalDays <= 0) {
            throw new PosServiceException(PosServiceException.Error.INVALID_RENTAL_DAYS, "Rental days must be greater than 0");
        }
        if(discountPercent < 0 || discountPercent > 100) {
            throw new PosServiceException(PosServiceException.Error.INVALID_DISCOUNT_PERCENT,
                    "Discount percent must be between 0 and 100");
        }

        // TODO - how to validate checkout date?

        Tool tool = toolService.getTool(toolCode)
                .orElseThrow(() -> new PosServiceException(PosServiceException.Error.INVALID_TOOL_CODE,
                        "Tool " + toolCode + " not found"));

        RentalPeriod rentalPeriod = rentalPeriodService.getRentalPeriod(tool.toolType(), checkoutDate, rentalDays);
        RentalCharge rentalCharge = pricingService.calculateRentalCharges(tool.toolType().getDailyCharge(),
                rentalPeriod.chargeDays(), discountPercent);

        RentalTransaction txn = new RentalTransaction(
                tool,
                rentalDays,
                checkoutDate,
                checkoutDate.plusDays(rentalDays),
                tool.toolType().getDailyCharge(),
                rentalPeriod.chargeDays(),
                rentalCharge.preDiscountCharge(),
                discountPercent,
                rentalCharge.discountAmount(),
                rentalCharge.preDiscountCharge().subtract(rentalCharge.discountAmount()));

        return rentalTransactionRepo.save(txn);
    }
}
