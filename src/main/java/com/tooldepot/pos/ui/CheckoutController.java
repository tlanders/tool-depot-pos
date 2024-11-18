package com.tooldepot.pos.ui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ui")
public class CheckoutController {
    @GetMapping("/checkout")
    public String showCheckout() {
        return "ui/checkout";
    }
}
