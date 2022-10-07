package com.example.demo.service;

public interface CommitService {

    int initRepository(int agentId,String repoName);


    //可以实现对文件属性的增删改;
    int commitFiles(int agentId,String repoName,String branchName,String content, String comment, String date);

    //新branch 操作:
    // creatBranch 再从新的branch上调用commitFilesOnMain 就行了;

//    int commitFilesOnNewBranch(int AgentId,String RepoName,String BranchName,String BaseBranch,String Content, String Comment, String Date);

}






