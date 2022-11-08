package com.example.demo.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/UserPage")
public class UserPageController {

    @RequestMapping("/{name}")
    public String showUserInfo(@PathVariable("name") String name){
        return "success";
    }

}