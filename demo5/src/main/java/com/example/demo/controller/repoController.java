package com.example.demo.controller;

import com.example.demo.service.repositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class repoController {
    @Autowired
    private repositoryService service;

    @RequestMapping("/add")
    int add(){
        return service.add(1, "abc", "first", "1000-01-01");
    }
}