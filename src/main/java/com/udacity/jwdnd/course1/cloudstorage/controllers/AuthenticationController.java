package com.udacity.jwdnd.course1.cloudstorage.controllers;

import com.udacity.jwdnd.course1.cloudstorage.dtos.SignupDto;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String getLoginView() {
        return "login";
    }

    @GetMapping("/signup")
    public String getSignupView(@ModelAttribute SignupDto signupFormDto) {
        return "signup";
    }

    @PostMapping("/signup")
    public String signupUser(@ModelAttribute SignupDto signupDto, Model model, RedirectAttributes redirectAttributes) {
        try {
            userService.createUser(signupDto);
            redirectAttributes.addFlashAttribute("signupSuccess", true);
            return "redirect:/login";
        } catch (ResponseStatusException e) {
            model.addAttribute("errorMessage", e.getReason());
            return "signup";
        }

    }


}
