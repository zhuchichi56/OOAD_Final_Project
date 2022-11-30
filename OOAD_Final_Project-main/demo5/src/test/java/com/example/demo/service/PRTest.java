package com.example.demo.service;

import com.example.demo.entity.Agent;
import com.example.demo.mapper.RepositoryMapper;
import com.example.demo.util.BranchUtil;
import com.example.demo.util.CommonMethod;
import com.example.demo.util.FileCoverUtil;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;

@SpringBootTest
public class PRTest {


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
    void test_init(String filename,String username, String reponame,String branch){
        agentService.deleteUser(localPath,"User_A");
        agentService.createUser(new Agent("User_A", "123456","icon"));
        agentService.deleteUser(localPath,"User_B");
        agentService.createUser(new Agent("User_B", "123456","icon"));
        Git repoA = repositoryService.initRepository(localPath, "User_A","Repo_001", 1);
    }


    @Test
    void Commit_fork(String filename,String username, String reponame,String branch){
        String testDirectory = "/Users/zhuhe/Desktop/TestRoot/"+ filename;
        File file = new File(testDirectory);
        commitService.commitFiles(localPath, username, reponame, branch, file);
    }




    @Test
    void Tesemd(){
        Commit_fork("fasdfa","User_A","Repo_001","master");
        Commit_fork("开发文档.md","User_A","Repo_001","master");
    }


    @Test
    void testfork() throws GitAPIException {
        agentService.deleteUser(localPath,"User_A");
        agentService.createUser(new Agent("User_A", "123456","icon"));
        agentService.deleteUser(localPath,"User_B");
        agentService.createUser(new Agent("User_B", "123456","icon"));
        Git repoA = repositoryService.initRepository(localPath, "User_A","Repo_001", 1);
        Git repoB = repositoryService.initRepository(localPath, "User_B","Repo_002", 1);
        Commit_fork("fasdfa","User_A","Repo_001","master");
        Commit_fork("fasdfa1","User_A","Repo_001","master");
        branchService.createBranch(repoA,"master","branch1");
        branchService.switchBranch(repoA,"branch1");
        Commit_fork("fasdfa2","User_A","Repo_001","master");

        branchService.switchBranch(repoA,"master");

        System.out.println(BranchUtil.getAllBranch(repoA));

//        branchService.createBranch(repoA,"master","branch1");
        repositoryService.forkRepository(localPath,"User_A", "Repo_001","master","User_B","Repo_001");
        branchService.switchBranch(repoA,"master");
        Commit_fork("test_2_r04.out","User_A","Repo_001","master");

    }



    @Test
    void testforkbranchinformation() throws GitAPIException {
        Git repoA = repositoryService.loadLocalRepository(localPath, "User_A","Repo_001");

        Git repoB = repositoryService.loadLocalRepository(localPath, "User_B","Repo_001");
        System.out.println(commitService.getCommitsByBranch(localPath, "User_B", "Repo_001", "master"));

//        Commit_fork("fasdfa","User_A","Repo_001","master");
//        Commit_fork("fasdfa1","User_A","Repo_001","master");
//        branchService.createBranch(repoA,"master","branch1");
//        branchService.switchBranch(repoA,"branch1");
//        Commit_fork("fasdfa2","User_A","Repo_001","master");
//        branchService.createBranch(repoA,"master","branch1");
//        repositoryService.forkRepository(localPath,"User_A", "Repo_001","master","User_B","Repo_001");
//




    }








    @Test
    public void testPR() throws GitAPIException, IOException {
        Git gitB = repositoryService.loadLocalRepository(localPath,"User_B","Repo_001");
        Git gitA = repositoryService.loadLocalRepository(localPath,"User_A","Repo_001");
        CommonMethod.CommitFile(gitB,localPath+File.separator+"User_B"+File.separator+"Repo_001","nihao");
        System.out.println(branchService.push(gitB, "master", "branch$"));
        gitA.commit().setMessage("merge").call();
        branchService.switchBranch(gitA,"master");
        CommonMethod.CommitFile(gitA,localPath+File.separator+"User_A"+File.separator+"Repo_001","nihaode");
        System.out.println(branchService.mergeNoCommit(gitA, "master", "branch$"));
    }
}










