package com.tooldepot.pos.ui;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.tooldepot.pos.api.RentalApiController.*;

@Slf4j
@Controller
@RequestMapping("/ui")
public class CheckoutController {
    @GetMapping("/checkout")
    public ModelAndView showCheckout() {
        log.debug("GET /ui/checkout");
        return new ModelAndView("ui/checkout", "checkoutFormBean", new CheckoutFormBean());
    }

    final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");

    @PostMapping("/checkout")
    public ModelAndView postCheckout(@Valid @ModelAttribute("checkoutFormBean") CheckoutFormBean checkoutFormBean,
                                     BindingResult errors) {
        log.debug("POST /ui/checkout, bean={}", checkoutFormBean);

        LocalDate checkoutDate = null;
        if(!errors.hasErrors()) {
            try {
                checkoutDate = LocalDate.parse(checkoutFormBean.getCheckoutDate(), dateFormatter);
            } catch (Exception e) {
                errors.rejectValue("checkoutDate", "checkoutDate.invalid", "Please enter a valid checkout date (MM/dd/yy)");
            }
        }

        if(errors.hasErrors()) {
            log.debug("Validation errors: {}", errors);
            return new ModelAndView("ui/checkout", "checkoutFormBean", checkoutFormBean);
        }

        try {
            CheckoutRequestModel checkoutRequestModel = new CheckoutRequestModel(
                    checkoutFormBean.getToolCode(), checkoutFormBean.getRentalDays(),
                    checkoutFormBean.getDiscountPercent(), checkoutDate);

            CheckoutResponseModel checkoutResponse = WebClient.create("http://localhost:8100")
                    .post()
                    .uri("/api/rentals")
                    .bodyValue(checkoutRequestModel)
                    .retrieve()
                    .onStatus(HttpStatusCode::is5xxServerError,
                            response -> response.bodyToMono(String.class).map(body -> new RuntimeException(body)))
                    .bodyToMono(CheckoutResponseModel.class)
                    .block();

            if(checkoutResponse.getResultCode() == 0) {
                return new ModelAndView("ui/agreement", "checkoutResponse", checkoutResponse);
            } else if(checkoutResponse.getResultCode() == 1) {
                errors.rejectValue("toolCode", "toolCode.notFound", "Tool not found");
            } else if(checkoutResponse.getResultCode() == 2) {
                errors.rejectValue("rentalDays", "rentalDays.invalid", "Rental days must be >= 1");
            } else if(checkoutResponse.getResultCode() == 3) {
                errors.rejectValue("discountPercent", "discountPercent.invalid", "Discount percent must be >= 0");
            }
        } catch(Exception e) {
            log.error("Error checking out", e);
        }

        return new ModelAndView("ui/checkout", "checkoutFormBean", checkoutFormBean);
    }
}
