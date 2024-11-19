package com.tooldepot.pos.ui;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
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
        Integer rentalDays = null;
        Integer discountPercent = null;
        if(!errors.hasErrors()) {
            try {
                checkoutDate = LocalDate.parse(checkoutFormBean.getCheckoutDate(), dateFormatter);
            } catch (Exception e) {
                errors.rejectValue("checkoutDate", "checkoutDate.invalid", "Please enter a valid checkout date (MM/dd/yy)");
            }
            try {
                rentalDays = Integer.valueOf(checkoutFormBean.getRentalDays());
            } catch (Exception e) {
                errors.rejectValue("rentalDays", "rentalDays.invalid", "Rental days must be 1 or greater");
            }
            try {
                discountPercent = Integer.valueOf(checkoutFormBean.getDiscountPercent());
            } catch (Exception e) {
                errors.rejectValue("discountPercent", "discountPercent.invalid", "Discount percent must be in the range 0-100");
            }
        }

        if(errors.hasErrors()) {
            log.debug("Validation errors: {}", errors);
            return new ModelAndView("ui/checkout", "checkoutFormBean", checkoutFormBean);
        }

        try {
            CheckoutRequestModel checkoutRequestModel = new CheckoutRequestModel(
                    checkoutFormBean.getToolCode(), rentalDays,
                    discountPercent, checkoutDate);

            RestTemplate restTemplate = new RestTemplate();
            String rentalURL = "http://localhost:8100/api/rentals";
            ResponseEntity<CheckoutResponseModel> responseEntity = restTemplate.postForEntity(rentalURL,
                    new HttpEntity<>(checkoutRequestModel),
                    CheckoutResponseModel.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return new ModelAndView("ui/agreement", "checkoutResponse", responseEntity.getBody());
            }
        } catch(HttpStatusCodeException httpStatusCodeException) {
            log.warn("Exception checking out", httpStatusCodeException);
            if(httpStatusCodeException.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY) {
                CheckoutResponseModel checkoutResponse = httpStatusCodeException.getResponseBodyAs(CheckoutResponseModel.class);
                if (checkoutResponse.getResultCode() == 1) {
                    errors.rejectValue("discountPercent", "discountPercent.invalid", "Discount percent must be in the range 0-100");
                } else if (checkoutResponse.getResultCode() == 2) {
                    errors.rejectValue("rentalDays", "rentalDays.invalid", "Rental days must be 1 or greater");
                } else if (checkoutResponse.getResultCode() == 3) {
                    errors.rejectValue("toolCode", "toolCode.notFound", "Tool not found");
                }
            }
        } catch(Exception e) {
            log.error("Exception checking out", e);
        }

        return new ModelAndView("ui/checkout", "checkoutFormBean", checkoutFormBean);
    }
}
