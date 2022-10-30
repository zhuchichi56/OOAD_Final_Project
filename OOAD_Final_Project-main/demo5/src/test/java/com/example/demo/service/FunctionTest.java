package com.example.demo.service;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class FunctionTest {
    @Autowired
    RepositoryService repositoryService;

    @Autowired
    BranchService branchService;

    @Autowired
    CommitService commitService;


    @Test
    void test() throws GitAPIException {
        try (Git repo = repositoryService.initRepository("C:\\Users\\12078\\Desktop\\大三上\\local", "test")) {

        }

    }
    @Test
    void testCreateBranch() throws GitAPIException {
        Git repo = repositoryService.loadLocalRepository("C:\\Users\\12078\\Desktop\\大三上\\local", "test");
        branchService.createBranch(repo, "branch1");
    }

    @Test
    void testInitRemote() {
        Git repoRemote = repositoryService.initRepository("C:\\Users\\12078\\Desktop\\大三上\\remote", "testRemote");
        repositoryService.cloneRepository("C:\\Users\\12078\\Desktop\\大三上\\remote", "C:\\Users\\12078\\Desktop\\大三上\\local","testRemote");

    }

    @Test
    void testBranch() throws GitAPIException {
        Git repo = repositoryService.loadLocalRepository("C:\\Users\\12078\\Desktop\\大三上\\local", "test");
        Ref branch1 = branchService.switchBranch(repo, "master");
        System.out.println(branch1.getName());
        Ref branch2 = branchService.switchBranch(repo, "branch1");
        System.out.println(branch2.getName());


    }

    @Test
    void testCommit(){
        commitService.commitFiles(2, "test", "branch1", null);
    }

    @Test
    void testMerge() throws GitAPIException, IOException {
        Git repo = repositoryService.loadLocalRepository("C:\\Users\\12078\\Desktop\\大三上\\local", "test");
        branchService.merge(repo, "master", "branch1");


    }

    @Test
    void testPull() throws GitAPIException {
        Git repo = repositoryService.loadLocalRepository("C:\\Users\\12078\\Desktop\\大三上\\local", "test");
        branchService.Pull("master", repo, "C:\\Users\\12078\\Desktop\\大三上\\remote");
    }

}
