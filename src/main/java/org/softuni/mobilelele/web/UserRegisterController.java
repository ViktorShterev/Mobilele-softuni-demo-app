package org.softuni.mobilelele.web;

import org.softuni.mobilelele.model.dto.ReCaptchaResponseDTO;
import org.softuni.mobilelele.model.dto.UserRegistrationDTO;
import org.softuni.mobilelele.service.ReCaptchaService;
import org.softuni.mobilelele.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/users")
@Controller
public class UserRegisterController {

    private final UserService userService;

    private final ReCaptchaService reCaptchaService;

    @Autowired
    public UserRegisterController(UserService userService, ReCaptchaService reCaptchaService) {
        this.userService = userService;
        this.reCaptchaService = reCaptchaService;
    }

    @GetMapping("/register")
    public String register() {
        return "auth-register";
    }

    @PostMapping("/register")
    public String register(UserRegistrationDTO userRegistrationDTO,
                           @RequestParam("g-recaptcha-response") String reCaptchaResponse) {

        //TODO: registration activation link

        boolean isBot = !this.reCaptchaService
                .verify(reCaptchaResponse)
                .map(ReCaptchaResponseDTO::isSuccess)
                .orElse(false);

        if (isBot) {
            return "redirect:/";
        }

        this.userService.registerUser(userRegistrationDTO);

        return "redirect:/login";
    }
}
