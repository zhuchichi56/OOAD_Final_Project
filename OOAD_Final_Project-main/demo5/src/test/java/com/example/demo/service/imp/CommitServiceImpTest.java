package com.example.demo.service.imp;


import com.example.demo.service.AgentService;
import com.example.demo.service.BranchService;
import com.example.demo.service.CommitService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CommitServiceImpTest {


    @Autowired
    BranchService branchService;


    @Autowired
    CommitService commitService;


    @Autowired
    AgentService agentService;


    @Test
    void initRepository() {


    }

    @Test
    void commitFilesOnMain(){



    }


    @Test
    void createNewBranchTest() {

    }



    @Test
    void rollBackTest(){

    }
//    @Test
//    void commitFilesOnNewBranch(){
//        commitService.commitFilesOnNewBranch(2,"hello7","branch3","main","nihaoshfasdfasdfasdfasfs23a","craete branch and update main files3","2002-02-11");
//    }

    //新创建一个分支操作;







}






//select 语句有点问题；

