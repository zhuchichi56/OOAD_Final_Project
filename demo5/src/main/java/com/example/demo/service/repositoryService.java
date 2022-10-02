package com.example.demo.service;

public interface repositoryService {
//    int add(int version_id, String content, String comment, String commit_time);

    void createNewRepo(String name, int AgentId, String content, String commit_time);

//    void createNewBranch();
}
