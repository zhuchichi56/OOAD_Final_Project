package com.example.demo.service;

import com.example.demo.entity.Agent;
import com.example.demo.entity.Issue;
import com.example.demo.mapper.RepositoryMapper;
import com.example.demo.util.FileCoverUtil;
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
    String localPath = "C:\\Users\\12078\\Desktop\\TEST\\Local";
    String remoteURL = "C:\\Users\\12078\\Desktop\\TEST\\Local\\User_B\\Repo_002";

    String testDirectory = "C:\\Users\\12078\\Desktop\\Spring-Project-thymeleaf";
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
        Git repo = repositoryService.initRepository(localPath, "User_A","Repo_001", 1);
        Git repo1 = repositoryService.initRepository(localPath, "User_B","Repo_002", 1);
    }
    @Test
    void testStar(){
        agentService.starNewRepo("User_B", "2666908d8f3dad76eb0b45899a556e0a071f32d7");
        agentService.inviteContributor("User_B", "2666908d8f3dad76eb0b45899a556e0a071f32d7");
    }
    @Test
    void testIssue(){
        String repoId = repositoryMapper.getRepoId("User_C","Repo_001");
        issueService.createIssueInRepo("User_C", repoId, "you have bug!!!");
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
    void temTest(){
        List<String> list =branchService.getContent(localPath+'\\'+"User_test_A"+'\\'+"REPO001",localPath+'\\'+"User_test_A"+'\\'+"REPO001"+'\\'+"practice7","master");
        list.forEach(o-> System.out.println(o));
    }





    @Test
    void testDelete(){
//        issueService.deleteCommentInIssue("f13edba5b6a064f0897e13d994fb77f39b624126");
        issueService.deleteIssueInRepo("f13edba5b6a064f0897e13d994fb77f39b624126");
    }



    @Test
    void testCommit(){
        File file = new File(testDirectory);
//        FileCoverUtil.updateFile("C:\\Users\\12078\\Desktop\\TEST\\Local\\User_test_A\\REPO001\\block\\Practice7", file);
        String dirPath = "C:\\Users\\12078\\Desktop\\TEST\\Local\\User_A\\Repo_001\\Spring-Project-thymeleaf";
        commitService.commitFiles(localPath, "User_A", "Repo_001", "master", file,dirPath);
//        commitService.getCommitsByBranch(localPath, "User_A", "Repo_001", "master");
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
