package com.photonest.account_service.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/account")
public class AccountController {

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello from Account Service!  to purvesh ";
    }

}
