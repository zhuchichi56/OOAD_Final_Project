package com.example.demo.service;


import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;

import java.io.IOException;

public interface BranchService {

    Ref createBranch(Git repository, RevCommit startPoint, String branchName) throws GitAPIException;

    Ref switchBranch(Git repository, String branchName) throws GitAPIException;

    void merge(Git repository, String baseBranch, String targetBranch) throws GitAPIException, IOException;

    Git Pull(String branchName, Git localRepository, String remotePath) throws GitAPIException;


}





