package com.example.demo.service;


import org.eclipse.jgit.lib.ObjectId;

//分支管理Service;
public interface BranchService {

    int createNewBranchOnOldBranch();
    /**
     * 给出ObjectId,并回滚至该版本
     * @param agentId
     * @param repoName
     * @param id
     * @return
     */
    int rollback(int agentId, String repoName, String id);

}





