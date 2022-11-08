package com.example.demo.service;

import com.example.demo.entity.Agent;
import com.example.demo.entity.Repo;

import java.awt.*;
import java.util.List;

public interface AgentService {
    String createUser(Agent agent);

    int updateUserName(String old, String latest);

    int updateUserIcon(String name, byte[] imageData);

    int inviteContributor(String contributorName, String repositoryId);

    int checkContributor(String contributorName, String repositoryId);

    int starNewRepo(String agentName, String repoId);

    int removeStarToRepo(String agentName, String repoId);

    List<Repo> allRepoAgentStar(String agentName);
}

