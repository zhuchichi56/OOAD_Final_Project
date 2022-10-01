package com.example.demo;

import com.example.demo.entity.repository;
import com.example.demo.mapper.repositoryMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Demo5ApplicationTests {

    @Autowired
    repositoryMapper mapper;

    @Test
    void contextLoads() {
        mapper.createNewRepo(new repository(0, 1, "abc", "first", "1000-01-01"));
    }

}
