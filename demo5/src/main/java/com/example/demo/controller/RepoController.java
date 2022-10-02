package com.example.demo.controller;

import com.example.demo.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class RepoController {
    @Autowired
    private RepositoryService service;

//    @RequestMapping("/add")
//    int add(){
//        return service.add(1, "abc", "first", "1000-01-01");
//    }
}