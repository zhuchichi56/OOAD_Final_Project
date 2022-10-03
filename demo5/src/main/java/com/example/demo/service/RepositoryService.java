package com.example.demo.service;

import com.example.demo.entity.RepoContent;

import java.util.List;

public interface RepositoryService {
//    int add(int version_id, String content, String comment, String commit_time);

    int createNewRepo(String name, int AgentId, String content, String commit_time);

//    void createNewBranch();

    /**
     *
     * @param repoName repository Name
     * @param agentId Agent Id
     * @param branchName Branch Name
     * @return all the RepoContent corresponding to the Branch
     */
    List<RepoContent> getAllRepoContentByBranch(String repoName, int agentId, String branchName);
}
