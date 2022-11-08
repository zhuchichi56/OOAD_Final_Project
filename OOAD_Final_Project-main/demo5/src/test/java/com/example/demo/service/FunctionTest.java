package com.example.demo.service;

import com.example.demo.entity.Agent;
import com.example.demo.entity.Repo;
import com.example.demo.mapper.RepositoryMapper;
import com.example.demo.util.DateParser;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.List;

@SpringBootTest
public class FunctionTest {
    String localPath = "C:\\Users\\12078\\Desktop\\TEST\\local";
    String remotePath = "C:\\Users\\12078\\Desktop\\TEST\\remote";
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
    void testUtil(){
        System.out.println(DateParser.getCurrentDate());
    }

    @Test
    void testNewAgent(){
        agentService.createUser(new Agent("User_test_A","123456798","12313"));
        agentService.createUser(new Agent("User_test_B","123456798","12313"));
    }
    @Test
    void testInitial() throws GitAPIException {

        Git repoA = repositoryService.initRepository(localPath, "User_test_A","REPO001");
        Git repoB = repositoryService.initRepository(localPath, "User_test_B","REPO002");
    }

    @Test
    void testCreateBranch() throws GitAPIException {
        Git repo = repositoryService.loadLocalRepository(localPath, "User_test_A","REPO001");
        branchService.createBranch(repo, "branch2");
    }

    @Test
    void testSwitchBranch() throws GitAPIException {
        Git repo = repositoryService.loadLocalRepository(localPath, "User_test_A","REPO001");
        branchService.switchBranch(repo, "master");
    }

    @Test
    void testCommit(){

        File file = new File("C:\\Users\\12078\\Desktop\\TEST\\local\\User_test_A\\REPO001\\");
        commitService.commitFiles(localPath ,"User_test_A", "REPO001", "master", file);
    }
    @Test
    void testClone() {

        repositoryService.cloneRepository("C:\\Users\\12078\\Desktop\\大三上\\local\\tester\\test", "C:\\Users\\12078\\Desktop\\大三上\\local","test","test");

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
    void testGetCommitsInfo(){
        List<RevCommit> list = commitService.getCommitsByBranch(localPath, "tester","test","master");
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
