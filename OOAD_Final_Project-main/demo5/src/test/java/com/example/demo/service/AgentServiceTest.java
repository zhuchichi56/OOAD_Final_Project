package com.example.demo.service;

import com.example.demo.entity.Agent;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;




@SpringBootTest
class AgentServiceTest {
    @Resource
    AgentService agentService;

    @Test
    void createName() {
        System.out.println(agentService.CreateName(new Agent("zzp")));
    }
}












