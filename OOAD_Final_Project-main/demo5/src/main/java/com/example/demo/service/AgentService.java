package com.example.demo.service;

import com.example.demo.entity.Agent;
import com.example.demo.entity.StaticRepo;

public interface AgentService {
    int createName(Agent agent);

    int inviteContributor(Agent agent, Agent contributor, String repoName);

    int checkContributor(Agent agent, Agent contributor, String repoName);
}

