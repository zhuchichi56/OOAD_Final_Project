package com.example.demo.service;

import com.example.demo.entity.Agent;
import com.example.demo.entity.Repo;

import java.awt.*;
import java.util.List;

public interface AgentService {
    int createUser(Agent agent);

    int deleteUser(String localPath, String name);


    int CheckUser( String agentname,String password);


    int updateUserName(String old, String latest);

    int updateUserPassword(String name, String oldPassword, String newPassword);

    int inviteContributor(String contributorName, String repositoryId);

    int checkContributor(String contributorName, String repositoryId);

    int removeContributor(String contributorName, String repositoryId);

    int starNewRepo(String agentName, String repoId);

    int removeStarToRepo(String agentName, String repoId);

    List<String> getContributors(String repositoryId);

    List<Repo> allRepoAgentStar(String agentName);

    List<Repo> getRepoByName(String UserName);


}

