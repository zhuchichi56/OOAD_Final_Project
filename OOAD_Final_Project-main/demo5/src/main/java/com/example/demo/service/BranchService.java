package com.example.demo.service;


import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.lib.ObjectId;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface BranchService {

    int rollback(Git repository, String branch,String id);
    Ref createBranch(Git repository, String branchName) throws GitAPIException;

    Ref switchBranch(Git repository, String branchName) throws GitAPIException;

    List<String> getContent(String path,String dirPath, String branch);

    void merge(Git repository, String baseBranch, String targetBranch) throws GitAPIException, IOException;

    Git pull(String branchName, Git localRepository, String remotePath) throws GitAPIException;

    void deleteBranch(Git repository, String branch) throws GitAPIException;



}





