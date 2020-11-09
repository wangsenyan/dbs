package com.dbs.controller;

import com.own.starter.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OwnStarterController {
    @Autowired
    HelloService helloService;

    @GetMapping("/own")
    public String hello() {
        return helloService.SayHello("haha");
    }
}
