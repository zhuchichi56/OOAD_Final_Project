package com.example.demo.mapper;

import com.example.demo.entity.RepoContent;
import com.example.demo.service.RepoContentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class RepoContentMapperTest {


    @Autowired
    RepoContentService repoContentService;

    @Test
    void createNewRepoContent() {
        System.out.println(repoContentService.createNewRepoContent(new RepoContent()));

    }
}