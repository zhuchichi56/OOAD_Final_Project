package com.example.demo.service;

import com.example.demo.entity.Agent;
import com.example.demo.entity.Issue;
import com.example.demo.mapper.RepositoryMapper;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.util.List;

@SpringBootTest
public class FunctionTest {
    String localPath = "C:\\Users\\vip\\Desktop\\TEST\\Local";
    String remoteURL = "C:\\Users\\vip\\Desktop\\TEST\\Local\\User_B\\Repo_002";

    String testDirectory = "C:\\Users\\vip\\Desktop\\Files";
    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RepositoryMapper repositoryMapper;
    @Autowired
    AgentService agentService;

    @Autowired
    IssueService issueService;

    @Autowired
    BranchService branchService;

    @Autowired
    CommitService commitService;

    @Test
    void testRegisterAgent(){
        agentService.createUser(new Agent("User_A", "123456","icon"));
        agentService.createUser(new Agent("User_B", "123456","icon"));
    }
    @Test
    void testInitial() throws GitAPIException {
        Git repo = repositoryService.initRepository(localPath, "User_A","Repo_001");
        Git repo1 = repositoryService.initRepository(localPath, "User_B","Repo_002");
    }
    @Test
    void testStar(){
        agentService.starNewRepo("User_A", "5d9f1778a1471c0f9cd24524dc0366756bc0041e");
        agentService.inviteContributor("User_A", "5d9f1778a1471c0f9cd24524dc0366756bc0041e");

    }
    @Test
    void testIssue(){
        String repoId = repositoryMapper.getRepoId("User_A","Repo_001");
        issueService.createIssueInRepo("User_A", repoId, "you have bug!!!");
        List<Issue> issues = issueService.showAllIssues(repoId);
        issueService.commentInIssue(issues.get(0).getIssueId(), "User_B","hello world");
        issueService.commentInIssue(issues.get(0).getIssueId(), "User_B","hello world");
        issueService.commentInIssue(issues.get(0).getIssueId(), "User_B","hello world");
        issueService.showAllCommentsInIssue(issues.get(0).getIssueId());

    }
    @Test
    void testUser(){
        agentService.updateUserName("User_A", "User_C");
    }





    @Test
    void testDelete(){

        issueService.deleteCommentInIssue("503b45e329c4d4e0afe671436193679f10de9108");
    }



    @Test
    void testCommit(){
        File file = new File(testDirectory);
        commitService.commitFiles(localPath, "User_A", "Repo_001", "master", file);
        commitService.getCommitsByBranch(localPath, "User_A", "Repo_001", "master");
    }

    @Test
    void testCreateBranch() throws GitAPIException {
        Git repo = repositoryService.loadLocalRepository(localPath, "User_A","Repo_001");
        branchService.createBranch(repo, "branch2");
        branchService.switchBranch(repo, "master");
    }

    @Test
    void testInitRemote() {

        repositoryService.cloneRepository(remoteURL, localPath,"User_A","Repo_002");
    }

    @Test
    void testBranch() throws GitAPIException {
        Git repo = repositoryService.loadLocalRepository(localPath, "User_A","Repo_001");
        Ref branch1 = branchService.switchBranch(repo, "master");
        System.out.println(branch1.getName());
        Ref branch2 = branchService.switchBranch(repo, "branch1");
        System.out.println(branch2.getName());
    }




    @Test
    void testMerge() throws GitAPIException, IOException {
        Git repo = repositoryService.loadLocalRepository(localPath, "User_A","Repo_001");
        branchService.merge(repo, "master", "branch1");


    }

    @Test
    void testPull() throws GitAPIException {
        Git repo = repositoryService.loadLocalRepository(localPath, "User_A","Repo_002");
        branchService.Pull("master", repo, remoteURL);
    }

}
