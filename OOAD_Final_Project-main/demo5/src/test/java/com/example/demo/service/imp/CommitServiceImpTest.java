package com.example.demo.service.imp;


import com.example.demo.service.AgentService;
import com.example.demo.service.BranchService;
import com.example.demo.service.CommitService;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommitServiceImpTest {


    @Autowired
    BranchService branchService;


    @Autowired
    CommitService commitService;


    @Autowired
    AgentService agentService;
    private String localPath = "C:\\Users\\12078\\Desktop\\大三上\\ooad\\test\\1\\test1";

    @Test
    void initRepository() {
        try {
            System.out.println(commitService.initRepository(1,"test1"));
        } catch (GitAPIException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void commitFilesOnMain(){
        File file = new File("C:\\Users\\12078\\Desktop\\大三上\\ooad\\MyWeb");
        commitService.commitFiles(1, "test1", "branch1", file);
    }



    @Test
    void rollBackTest(){
        System.out.println(branchService.rollback(1,"test1","79cc7c4afd801628bbe8c9c97fb20352b794a319"));
    }

    @Test
    void getAllCommits(){
        commitService.getCommitsByBranch(1,"test1", "branch1");
    }
//    @Test
//    void commitFilesOnNewBranch(){
//        commitService.commitFilesOnNewBranch(2,"hello7","branch3","main","nihaoshfasdfasdfasdfasfs23a","craete branch and update main files3","2002-02-11");
//    }

    //新创建一个分支操作;







}






//select 语句有点问题；

