package com.example.demo.service.imp;


import com.example.demo.entity.Agent;
import com.example.demo.mapper.AgentMapper;
import com.example.demo.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentServiceImp implements AgentService {

    @Autowired
    AgentMapper agentMapper;


    //直接返回User的自增主键；
    @Override
    public int createName(Agent agent) {

        int count = agentMapper.createAgent(agent);
        return agent.getAgentId();
    }







}
