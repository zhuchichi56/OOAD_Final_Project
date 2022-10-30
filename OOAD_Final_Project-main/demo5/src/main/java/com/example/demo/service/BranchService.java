package com.example.demo.service;


import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.lib.ObjectId;

import java.io.IOException;

public interface BranchService {

    /**
     * 给出ObjectId,并回滚至该版本
     * @param agentId
     * @param repoName
     * @param id
     * @return
     */
    int rollback(int agentId, String repoName, String id);
    Ref createBranch(Git repository, String branchName) throws GitAPIException;

    Ref switchBranch(Git repository, String branchName) throws GitAPIException;

    void merge(Git repository, String baseBranch, String targetBranch) throws GitAPIException, IOException;

    Git Pull(String branchName, Git localRepository, String remotePath) throws GitAPIException;


}





