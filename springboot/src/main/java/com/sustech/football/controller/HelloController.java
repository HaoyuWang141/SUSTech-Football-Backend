package com.sustech.football.controller;

import com.sustech.football.entity.User;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
@CrossOrigin
public class HelloController {

    @GetMapping("")
    public String hello() {
        System.out.println("hello");
        return "hello";
    }
}
