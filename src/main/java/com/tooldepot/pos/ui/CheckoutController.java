package com.tooldepot.pos.ui;

import com.tooldepot.pos.domain.RentalTransaction;
import com.tooldepot.pos.service.CheckoutService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ValidationUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Controller
@RequestMapping("/ui")
public class CheckoutController {
    private CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

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
            RentalTransaction rentalTransaction = checkoutService.checkout(checkoutFormBean.getToolCode(),
                    checkoutFormBean.getRentalDays(),
                    checkoutFormBean.getDiscountPercent(), checkoutDate);

            return new ModelAndView("ui/agreement", "rentalTransaction", rentalTransaction);
        } catch(Exception e) {
            log.error("Error checking out", e);
        }

        return new ModelAndView("ui/checkout", "checkoutFormBean", checkoutFormBean);
    }
}
