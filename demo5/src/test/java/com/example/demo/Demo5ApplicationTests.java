package com.example.demo;

import com.example.demo.entity.repository;
import com.example.demo.service.repositoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Demo5ApplicationTests {

    @Autowired
    repositoryService repositoryService;

    @Test
    void contextLoads() {
        repositoryService.createNewRepo("first",1,"sadas", "1000-09-01");
    }

}
