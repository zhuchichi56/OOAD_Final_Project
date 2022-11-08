package com.example.demo.service;

import org.eclipse.jgit.revwalk.RevCommit;

import java.io.File;
import java.util.List;

public interface CommitService {

    /**
     * 指定分支提交数据,多用户提交情况还未考虑
     * @param agentName
     * @param repoName
     * @param branch
     * @param file
     * @return
     */
    //可以实现对文件属性的增删改;
    int commitFiles(String localPath, String agentName, String repoName, String branch, File file);

    /**
     * 返回所有提交版本的ID
     * @param agentName
     * @param repoName
     * @param branch
     * @return
     */
    List<RevCommit> getCommitsByBranch(String localPath, String agentName, String repoName, String branch);

    /**
     * clone分支的最新版本
     * @param agentId
     * @param repoName
     * @param branch
     * @return
     */

}






