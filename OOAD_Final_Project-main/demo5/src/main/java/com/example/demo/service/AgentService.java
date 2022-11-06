package com.example.demo.service;

import com.example.demo.entity.Agent;

public interface AgentService {
    String createUser(Agent agent);

    int inviteContributor(String contributorName, String repositoryId);

    int checkContributor(String contributorName, String repositoryId);
}

