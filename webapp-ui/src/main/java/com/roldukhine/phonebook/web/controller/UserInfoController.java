package com.roldukhine.phonebook.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserInfoController {
    @RequestMapping("/user")
    public Principal ouathUserInfo(Principal principal) {
        return principal;
    }
}
