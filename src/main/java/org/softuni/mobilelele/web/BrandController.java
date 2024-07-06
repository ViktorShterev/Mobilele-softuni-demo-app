package org.softuni.mobilelele.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BrandController {

    @GetMapping("/brands")
    public String getAllBrands() {
        return "brands";
    }
}
