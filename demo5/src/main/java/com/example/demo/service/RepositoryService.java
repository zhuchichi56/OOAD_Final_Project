package com.example.demo.service;

public interface RepositoryService {
//    int add(int version_id, String content, String comment, String commit_time);

    int createNewRepo(String name, int AgentId, String content, String commit_time);

//    void createNewBranch();
}
