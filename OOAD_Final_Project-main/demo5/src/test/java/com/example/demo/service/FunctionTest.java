package com.example.demo.service;

import com.example.demo.entity.Agent;
import com.example.demo.entity.Repo;
import com.example.demo.mapper.RepositoryMapper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

@SpringBootTest
public class FunctionTest {
    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RepositoryMapper repositoryMapper;
    @Autowired
    AgentService agentService;

    @Autowired
    BranchService branchService;

    @Autowired
    CommitService commitService;

    @Test
    void testRegisterAgent(){
        agentService.createUser(new Agent("tester2", "123456"));
    }


    @Test
    void testInitial() throws GitAPIException {
        Git repo = repositoryService.initRepository("C:\\Users\\12078\\Desktop\\大三上\\local", "tester","test");
    }

    @Test
    void testCreateBranch() throws GitAPIException {
        Git repo = repositoryService.loadLocalRepository("C:\\Users\\12078\\Desktop\\大三上\\local", "tester","test");
        branchService.createBranch(repo, "branch2");
    }

    @Test
    void testInitRemote() {
//        Git repoRemote = repositoryService.initRepository("C:\\Users\\12078\\Desktop\\大三上\\remote","tester" ,"testRemote");
        repositoryService.cloneRepository("C:\\Users\\12078\\Desktop\\大三上\\remote", "C:\\Users\\12078\\Desktop\\大三上\\local","tester","test");
    }

    @Test
    void testBranch() throws GitAPIException {
        Git repo = repositoryService.loadLocalRepository("C:\\Users\\12078\\Desktop\\大三上\\local", "tester","test");
        Ref branch1 = branchService.switchBranch(repo, "master");
        System.out.println(branch1.getName());
        Ref branch2 = branchService.switchBranch(repo, "branch1");
        System.out.println(branch2.getName());
    }

    @Test
    void testCommit(){
        File file = new File("C:\\Users\\12078\\Desktop\\grade three 1\\AI\\lab\\Practice7");
        commitService.commitFiles("tester", "test", "master", file);
    }

    @Test
    void testMerge() throws GitAPIException, IOException {
        Git repo = repositoryService.loadLocalRepository("C:\\Users\\12078\\Desktop\\大三上\\local", "tester","test");
        branchService.merge(repo, "master", "branch1");


    }

//    @Test
//    void testPull() throws GitAPIException {
//        Git repo = repositoryService.loadLocalRepository("C:\\Users\\12078\\Desktop\\大三上\\local", "tester","test");
//        branchService.Pull("master", repo, "C:\\Users\\12078\\Desktop\\大三上\\remote");
//    }

}
