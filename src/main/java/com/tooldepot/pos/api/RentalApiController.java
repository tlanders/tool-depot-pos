package com.tooldepot.pos.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.tooldepot.pos.domain.Tool;
import com.tooldepot.pos.service.CheckoutService;
import com.tooldepot.pos.service.PosServiceException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.record.RecordModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * REST API controller for rental services.
 */
@RestController
@RequestMapping("/api/rentals")
@Slf4j
public class RentalApiController {
    @Autowired
    private CheckoutService checkoutService;

    @PostMapping
    public ResponseEntity<CheckoutResponseModel> checkout(@RequestBody @NonNull CheckoutRequestModel checkoutRequestModel) {
        log.debug("checkout called - toolCode={}, rentalDays={}, discountPercent={}, checkoutDate={}",
                checkoutRequestModel.toolCode(), checkoutRequestModel.rentalDays(), checkoutRequestModel.discountPercent(), checkoutRequestModel.checkoutDate());
        try {
            ModelMapper modelMapper = new ModelMapper().registerModule(new RecordModule());
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

            CheckoutResponseModel checkoutResponse = modelMapper.map(
                    checkoutService.checkout(checkoutRequestModel.toolCode(), checkoutRequestModel.rentalDays(),
                            checkoutRequestModel.discountPercent(), checkoutRequestModel.checkoutDate()),
                    CheckoutResponseModel.class);
            checkoutResponse.setMessage("success");

            return ResponseEntity.status(HttpStatus.CREATED).body(checkoutResponse);
        } catch(PosServiceException posServiceException) {
            return ResponseEntity.unprocessableEntity().body(
                    new CheckoutResponseModel(posServiceException.getError().getErrorCode(), posServiceException.getMessage()));
        }
    }

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(callSuper = true)
    public static class CheckoutResponseModel extends BaseApiResponseModel {
        private Tool tool;
        private int rentalDays;
        @JsonFormat(pattern = "MM/dd/yy")
        private LocalDate checkoutDate;
        @JsonFormat(pattern = "MM/dd/yy")
        private LocalDate dueDate;
        private BigDecimal dailyRentalCharge;
        private int chargeDays;
        private BigDecimal preDiscountCharge;
        private int discountPercent;
        private BigDecimal discountAmount;
        private BigDecimal finalCharge;

        public CheckoutResponseModel(int resultCode, String message) {
            this.resultCode = resultCode;
            this.message = message;
        }
    }

    public record CheckoutRequestModel(
            String toolCode,
            int rentalDays,
            int discountPercent,
            @JsonFormat(pattern = "M/d/yy")  LocalDate checkoutDate)
    { }
}
