package com.example.demo.service;


import com.example.demo.entity.Agent;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;




@SpringBootTest
class AgentServiceTest {
    @Autowired
    AgentService agentService;
    @Autowired
    CommitService commitService;

    @Test
    void createName() {
        System.out.println(agentService.CreateName(new Agent("test20")));
    }

    @Test
    void createRepo(){
        System.out.println(commitService.initRepository(1,"TestRepository"));
    }
}












