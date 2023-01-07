package com.andrey.spring.http.controller;

import com.andrey.spring.dto.LoginDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
//@SessionAttributes({"login"})
public class LoginController {

    @GetMapping("/login")
    public String loginPage(){
        return "user/login";
    }
}
