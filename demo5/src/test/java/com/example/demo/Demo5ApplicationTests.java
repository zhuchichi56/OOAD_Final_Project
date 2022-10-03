package com.example.demo;


import com.example.demo.service.RepositoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Demo5ApplicationTests {

    @Autowired
    RepositoryService repositoryService;

    @Test
    void contextLoads() {
        System.out.println(repositoryService.createNewRepo("sixth",5,"test6", "2022-10-03"));
    }

}
