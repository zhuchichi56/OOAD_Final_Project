package com.example.demo.service;

import com.example.demo.entity.Agent;
import com.example.demo.entity.Issue;
import com.example.demo.mapper.RepositoryMapper;
import com.example.demo.util.BranchUtil;
import com.example.demo.util.FileCoverUtil;
import com.example.demo.util.ZipUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeResult;
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

    String remoteURL = "/Users/zhuhe/Desktop/Jgit";

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
    void Commit_fork(String filename,String username, String reponame,String branch){
        String testDirectory = "/Users/zhuhe/Desktop/TestRoot/"+ filename;
        File file = new File(testDirectory);
        commitService.commitFiles(localPath, username, reponame, branch, file);
    }



    @Test
    void testfork(){
        agentService.deleteUser(localPath,"User_A");
        agentService.createUser(new Agent("User_A", "123456","icon"));
        agentService.deleteUser(localPath,"User_B");
        agentService.createUser(new Agent("User_B", "123456","icon"));

        Git repoA = repositoryService.initRepository(localPath, "User_A","Repo_001", 1);
        Git repoB = repositoryService.initRepository(localPath, "User_B","Repo_002", 1);
        Commit_fork("fasdfa","User_A","Repo_001","master");
        Commit_fork("fasdfa1","User_A","Repo_001","master");
        repositoryService.forkRepository(localPath,"User_A", "Repo_001","master","User_B","Repo_001");

    }




    @Test
    void TestCommitf() throws GitAPIException {
        Git repo = repositoryService.loadLocalRepository(localPath,"User_A","Repo_001");
//        commitService.committopath(localPath,"User_A","Repo_001","master","",new File("/Users/zhuhe/Temp"));
         commitService.committopath(localPath,"User_A","Repo_001","master","",new File("/Users/zhuhe/Temp"));
    }





    @Test
    void TestSwitchBranch() throws GitAPIException {
        Git repo = repositoryService.loadLocalRepository(localPath,"User_A","Repo_001");
        branchService.switchBranch(repo,"branch$");
        branchService.switchBranch(repo,"master");
        System.out.println(BranchUtil.getAllBranch(repo));
    }




    /**
     * 测试commit操作
     * */
    @Test
    void Commit(String filename,String branch){
        String testDirectory = "/Users/zhuhe/Desktop/TestRoot/"+ filename;
        File file = new File(testDirectory);
//        String repoPath = localPath + File.separator +"User_A" + File.separator +"Repo_001";
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

//        String a = String.valueOf(branchService.GetConflict (repo, "master", "branch1"));
        Ref branch1 = branchService.switchBranch(repo, "master");
    }

    @Test
    void delete() throws GitAPIException, IOException {

        Git repo = repositoryService.initRepository(localPath, "User_A","Repo_001", 1);
        Commit("fasdfa","master");
        branchService.createBranch(repo,"master","branch1");
        Commit("fasdfa1","branch1");
        branchService.createBranch(repo,"branch1","branch2");
        Commit("fasdfa2","branch2");
        branchService.switchBranch(repo,"branch1");
//        FileCoverUtil.deleteFolder(new File(localPath+File.separator+))
    }







    String Empty = "/Users/zhuhe/Desktop/Empty";
    @Test
    void Empty() throws GitAPIException, IOException {
        agentService.deleteUser(localPath,"User_A");
        agentService.createUser(new Agent("User_A", "123456","icon"));
        Git repo = repositoryService.initRepository(localPath, "User_A","Repo_001", 1);
        Commit("fasdfa","master");
        branchService.createBranch(repo,"master","branch1");
        Commit("fasdfa1","branch1");
        branchService.createBranch(repo,"branch1","branch2");



        commitService.commitFiles(localPath,"User_A","Repo_001","branch2",new File(Empty)
//                localPath+File.separator+"User_A"+File.separator+"Repo_001"
                );
//        Commit("fasdfa2","branch2");

        branchService.switchBranch(repo,"branch1");
//        FileCoverUtil.deleteFolder(new File(localPath+File.separator+))
    }




    @Test
    void TestConflict() throws GitAPIException, IOException {
        agentService.deleteUser(localPath,"User_A");
        agentService.createUser(new Agent("User_A", "123456","icon"));
        Git repo = repositoryService.initRepository(localPath, "User_A","Repo_001", 1);
        Commit("test1.c","master");
        branchService.createBranch(repo,"master","branch1");
        Commit("test2.c","branch1");
        branchService.switchBranch(repo,"master");
        FileCoverUtil.WriteIn(new File("/Users/zhuhe/Desktop/TestRoot/test2.c"));
        Commit("test2.c","master");

        List<String> list = branchService.merge(repo,"master","branch1");
        System.out.println(list.toString());
    }


    //


    @Test
    void TestConflictshow() throws GitAPIException, IOException {
        agentService.deleteUser(localPath,"User_A");
        agentService.createUser(new Agent("User_A", "123456","icon"));
        Git repo = repositoryService.initRepository(localPath, "User_A","Repo_001", 1);
        Commit("test1.c","master");
        branchService.createBranch(repo,"master","branch1");
        Commit("test2.c","branch1");
        branchService.switchBranch(repo,"master");
        FileCoverUtil.WriteIn(new File("/Users/zhuhe/Desktop/TestRoot/test2.c"));
        Commit("test2.c","master");
//        String a = String.valueOf(branchService.GetConflict (repo, "master", "branch1"));
//        System.out.println(a);
    }





    void TestPR() throws GitAPIException, IOException {

        agentService.deleteUser(localPath,"User_A");
        agentService.createUser(new Agent("User_A", "123456","icon"));
        Git repo = repositoryService.initRepository(localPath, "User_A","Repo_001", 1);
        Commit("test1.c","master");
        branchService.createBranch(repo,"master","branch1");
        Commit("test2.c","branch1");
        branchService.switchBranch(repo,"master");
        FileCoverUtil.WriteIn(new File("/Users/zhuhe/Desktop/TestRoot/test2.c"));
        Commit("test2.c","master");

        List<String> list = branchService.merge(repo,"master","branch1");
        System.out.println(list.toString());
    }


    @Test
    void getBranchList() throws GitAPIException, IOException {
        Git repo = repositoryService.initRepository(localPath, "User_A","Repo_001", 1);
        List<String> branchname = BranchUtil.getAllBranch(repo);
        for (int i = 0 ; i < branchname.size(); i ++){
            System.out.println(branchname.get(i));
        }
    }

    @Test
    void TestDelete() throws GitAPIException {
        Git repo = repositoryService.loadLocalRepository(localPath,"User_A","Repo_001");
        List<String> a = branchService.deleteBranchForce(repo, "branch3");
    }


    @Test
    void Ts() throws GitAPIException {
        Git repo = repositoryService.loadLocalRepository(localPath,"User_A","Repo_001");
        Ref branch1 = branchService.switchBranch(repo, "master");
    }




    @Test
    void testStar(){


         int a =agentService.starNewRepo("ab", "d1833952e265cc0c0b0fafa3a1d97a340e10f5dc");
         System.out.println(a);

    }


    @Test
    void testIssue(){
        String repoId = repositoryMapper.getRepoId("User_C","Repo_001");

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



//    @Test
//    void testInitRemote() {
//        repositoryService.cloneRepository(remoteURL, localPath,"User_A","Repo_002");
//    }



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

