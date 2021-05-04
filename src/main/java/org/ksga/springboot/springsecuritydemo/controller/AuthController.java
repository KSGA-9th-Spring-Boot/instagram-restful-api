package org.ksga.springboot.springsecuritydemo.controller;

import org.ksga.springboot.springsecuritydemo.security.service.UserDetailsImpl;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/auth/login")
    public String login() {
        return "auth/login";
    }

}