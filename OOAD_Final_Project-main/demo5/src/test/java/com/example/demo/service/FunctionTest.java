package com.example.demo.service;

import com.example.demo.entity.Agent;
import com.example.demo.entity.Issue;
import com.example.demo.mapper.RepositoryMapper;
import com.example.demo.util.BranchUtil;
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
public class FunctionTest  {


    String localPath = "/Users/zhuhe/Desktop/Jgit";

    String remoteURL = "C:\\Users\\12078\\Desktop\\TEST\\Local\\User_B\\Repo_002";
    String testDirectory = "/Users/zhuhe/Desktop/Spring-Project-thymeleaf";
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
        agentService.createUser(new Agent("User_1", "123456","icon"));
        agentService.createUser(new Agent("User_2", "123456","icon"));
    }




    @Test
    void TestRepoPage() throws GitAPIException {
        String agentName = "C";
        String repoName = "R1";
        String targetPath = localPath+File.separator+agentName+File.separator+repoName+File.separator+"MoveFile";

        agentService.createUser(new Agent(agentName, "123456","icon"));
        Git repo = repositoryService.initRepository(localPath, agentName,repoName, 1);
        //还没创建
//        branchService.switchBranch(repo, "branch1");
        File file = new File(testDirectory);
        commitService.commitFiles(localPath, agentName, repoName, "master", file ,targetPath);

    }



    @Test
    void TestBranch() throws GitAPIException {
        String agentName = "C";
        String repoName = "R1";
        String targetPath = localPath+File.separator+agentName+File.separator+repoName+File.separator+"MoveFile2";
        Git repo = repositoryService.loadLocalRepository(localPath,agentName,repoName);
        branchService.createBranch(repo,"branch1");
//        branchService.switchBranch(repo,"branch1");
        File file = new File(testDirectory);
        commitService.commitFiles(localPath, agentName, repoName, "branch1", file ,targetPath);

    }







    @Test
    void TestSwitchBranch() throws GitAPIException {
        String agentName = "C";
        String repoName = "R1";
        String targetPath = localPath+File.separator+agentName+File.separator+repoName+File.separator+"MoveFile4";
        Git repo = repositoryService.loadLocalRepository(localPath,agentName,repoName);
//        branchService.createBranch(repo,"branch4");
        branchService.switchBranch(repo,"branch2");
//
//        File file = new File(testDirectory);
//        commitService.commitFiles(localPath, agentName, repoName, "branch4", file ,targetPath);



    }









    @Test
    void testInitial() throws GitAPIException {
        Git repo = repositoryService.initRepository(localPath, "User_2","Repo_001", 1);
//        Git repo1 = repositoryService.initRepository(localPath, "User_B","Repo_002", 1);
        Git rep1o = repositoryService.initRepository(localPath, "User_2","Repo_002", 1);
        Git repo2 = repositoryService.initRepository(localPath, "User_2","Repo_003", 1);
        Git repo3 = repositoryService.initRepository(localPath, "User_2","Repo_004", 1);
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
        List<String> list =branchService.getContent(
                localPath+File.separator+
                "User_2"+File.separator+"REPO001",
                localPath+File.separator+"User_test_A"+File.separator+"REPO001"+File.separator+"practice7","master");

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
        System.out.println(file);
        commitService.commitFiles(localPath, "User_2", "Repo_001", "master", file ,localPath+File.separator+"User_2"+File.separator+"Repo_001"+File.separator+"master"+File.separator+"filename");
//        commitService.getCommitsByBranch(localPath, "User_3", "Repo_001", "master");
    }




    @Test
    void testCreateBranch() throws GitAPIException {
        Git repo = repositoryService.loadLocalRepository(localPath, "User_2","Repo_001");
        branchService.switchBranch(repo, "master");
//        branchService.switchBranch(repo, "master");
    }






    @Test
    void testBranchList() throws GitAPIException {
        Git repo = repositoryService.loadLocalRepository(localPath, "User_2","Repo_001");
        System.out.println(BranchUtil.getAllBranch(repo));
    }





    @Test
    void testInitRemote() {
        repositoryService.cloneRepository(remoteURL, localPath,"User_A","Repo_002");
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

