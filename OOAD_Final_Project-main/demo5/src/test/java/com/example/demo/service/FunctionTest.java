package com.example.demo.service;

import com.example.demo.entity.Agent;
import com.example.demo.entity.Issue;
import com.example.demo.mapper.RepositoryMapper;
import com.example.demo.util.BranchUtil;
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


    String localPath = "/Users/zhuhe/Desktop/Jgit";

    String remoteURL = "C:\\Users\\12078\\Desktop\\TEST\\Local\\User_B\\Repo_002";

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




    /*
    * User 的 创建和删除
    * */
    @Test
    void testRegisterAgent(){
        agentService.createUser(new Agent("User_A", "123456","icon"));
//        agentService.createUser(new Agent("User_1", "123456","icon"));
    }


    @Test
    void DeleteUser(){
        agentService.deleteUser(localPath,"User_A");
    }


    @Test
    void testRepoInit() throws GitAPIException {
        Git repo = repositoryService.initRepository(localPath, "User_A","Repo_001", 1);
    }


    /**
     * 测试commit操作
     * */
    @Test
    void Commit(String filename,String branch){
        String testDirectory = "/Users/zhuhe/Desktop/"+ filename;
        File file = new File(testDirectory);
        System.out.println(file);
        commitService.commitFiles(localPath, "User_A", "Repo_001", branch, file);
    }




    @Test
    void TestCommit(){
//        Commit("unkown","master");
//        Commit("unkown1","master");
        Commit("unkown2","master");
    }



    @Test
    void TestGetCommit(){
        List<RevCommit> commitlist = commitService.getCommitsByBranch(localPath,"User_A", "Repo_001", "master");
    }


    @Test
    void TestRollback(){
        Git repo = repositoryService.loadLocalRepository(localPath,"User_A","Repo_001");

    }

    /**
     * 测试分支管理
     * */
    @Test
    void TestBranchCreate() throws GitAPIException {
        Git repo = repositoryService.loadLocalRepository(localPath,"User_A","Repo_001");
        Commit("fasdfa","master");
        branchService.createBranch(repo,"master","branch1");
        Commit("fasdfa","branch1");
        branchService.createBranch(repo,"branch1","branch2");
        Commit("fasdfa","branch2");
        branchService.createBranch(repo,"branch2","branch3");
        Commit("fasdfa","branch3");
    }












    @Test
    void TestSwitchBranch() throws GitAPIException {
        Git repo = repositoryService.loadLocalRepository(localPath,"User_A","Repo_001");
        branchService.switchBranch(repo,"master");
        System.out.println(BranchUtil.getAllBranch(repo));
    }


    @Test
    void TestMergeBranch() throws GitAPIException, IOException {
        Git repo = repositoryService.loadLocalRepository(localPath,"User_A","Repo_001");
        branchService.merge(repo,"version3","version1");
        branchService.switchBranch(repo,"version4");
//        branchService.deleteBranch(repo,"version1");
//        testCommit();
    }





    @Test
    void TestDeleteBranch() throws GitAPIException, IOException {
        Git repo = repositoryService.loadLocalRepository(localPath,"User_A","Repo_001");
        branchService.switchBranch(repo,"master");
        branchService.deleteBranch(repo,"version1");
        branchService.deleteBranch(repo,"version2");
        branchService.deleteBranch(repo,"version3");
        branchService.deleteBranch(repo,"version4");

    }


    /**
     * PR 功能实现;
     * */

    /*
    * fastmerge
    * */

   /*
   *
   *单个用户
   * */


    @Test
    void TestFastmerge() throws GitAPIException, IOException {
        agentService.deleteUser(localPath,"User_A");
        agentService.createUser(new Agent("User_A", "123456","icon"));
        Git repo = repositoryService.initRepository(localPath, "User_A","Repo_001", 1);
        Commit("fasdfa","master");
        branchService.createBranch(repo,"master","branch1");
        Commit("fasdfa1","branch1");

        branchService.createBranch(repo,"branch1","branch2");
        Commit("fasdfa2","branch2");


        List<String> list_ = branchService.merge(repo, "master", "branch2");
        for (int i = 0 ; i < list_.size(); i ++){
            System.out.println(list_.get(i));
        }
        Ref branch1 = branchService.switchBranch(repo, "master");

    }



    @Test
    void Ts() throws GitAPIException {
        Git repo = repositoryService.loadLocalRepository(localPath,"User_A","Repo_001");
        Ref branch1 = branchService.switchBranch(repo, "master");
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
        System.out.println(agentService.updateUserName("User_A", "User_D"));
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
    void testContributor(){
        agentService.inviteContributor("User_A", "ee991272b5ff8e67c66cc346714bb41b0d77e0b5");
        agentService.getContributors("ee991272b5ff8e67c66cc346714bb41b0d77e0b5").forEach(System.out::println);
    }

    @Test
    void  testUpdateUser(){
        agentService.createUser(new Agent("User_A","123456","1"));
//        agentService.updateUserName("User_f", "User_A");
//        agentService.updateUserPassword("User_f","123456","654321");
        agentService.deleteUser(localPath,"User_A");
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

//    @Test
//    void testPull() throws GitAPIException {
//        Git repo = repositoryService.loadLocalRepository(localPath, "User_A","Repo_002");
//        branchService.pull("master", repo, remoteURL);
//    }


}

