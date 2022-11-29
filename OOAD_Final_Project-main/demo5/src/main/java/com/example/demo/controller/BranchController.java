package com.example.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.BranchService;
import com.example.demo.service.RepositoryService;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(value = "/Branch")
public class BranchController {
    String localPath = "/Users/zhuhe/Desktop/Jgit";

    String testDirectory = "/Users/zhuhe/Desktop/Jgit.md";


    @Autowired
    RepositoryService repositoryService;
    @Autowired

    BranchService branchService;


    @ResponseBody
    @RequestMapping(value ="/create/{agentName}/{repoName}/{baseName}/{targetbranch}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public int createBranch(@PathVariable("agentName") String agentName,
                               @PathVariable("repoName") String repoName,
                            @PathVariable("baseName") String baseName,
                            @PathVariable("targetbranch") String targetbranch
    ) throws GitAPIException {
        Git repository = repositoryService.loadLocalRepository(localPath, agentName, repoName);
        return branchService.createBranch(repository, baseName,targetbranch)==null ? 0 : 1;

    }



//
//    @ResponseBody
//    @RequestMapping(value ="/switchBranch/{agentName}/{repoName}/{branchName}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//    public String switchBranch(@PathVariable("agentName") String agentName,
//                           @PathVariable("repoName") String repoName,
//                           @PathVariable("branchName") String branchName
//    ) throws GitAPIException {
//        JSONObject result = new JSONObject();
//        Git repository = repositoryService.loadLocalRepository(localPath, agentName, repoName);
//        branchService.switchBranch(repository, branchName);
//        result.put("status", "Switch Branch: " + branchName);
//        return result.toJSONString();
//    }



    @ResponseBody
    @RequestMapping(value ="/deleteForce/{agentName}/{repoName}/{branchName}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public int deleteBranchForce(@PathVariable("agentName") String agentName,
                               @PathVariable("repoName") String repoName,
                               @PathVariable("branchName") String branchName

    ) throws GitAPIException {
        Git repository = repositoryService.loadLocalRepository(localPath, agentName, repoName);
        List<String> a = branchService.deleteBranchForce(repository, branchName);
        return a == null ? 0 : 1;
    }


    @ResponseBody
    @RequestMapping(value ="/delete/{agentName}/{repoName}/{branchName}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public int deleteBranch(@PathVariable("agentName") String agentName,
                                 @PathVariable("repoName") String repoName,
                                 @PathVariable("branchName") String branchName

    ) throws GitAPIException {
        Git repository = repositoryService.loadLocalRepository(localPath, agentName, repoName);
        List<String> a = branchService.deleteBranch(repository, branchName);
        return a == null ? 0 : 1;
    }












//
//    /**
//     * 目前未测;
//    * */
//    @ResponseBody
//    @RequestMapping(value ="/merge/{agentName}/{repoName}/{baseBranchName}/{targetBranchName}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//    public String mergeBranch(@PathVariable("agentName") String agentName,
//                               @PathVariable("repoName") String repoName,
//                               @PathVariable("baseBranchName") String baseBranchName,
//                              @PathVariable("targetBranchName") String targetBranchName
//
//    ) throws GitAPIException, IOException {
//        JSONObject result = new JSONObject();
//
//        Git repository = repositoryService.loadLocalRepository(localPath, agentName, repoName);
//        List<String> responce  = branchService.merge(repository, baseBranchName, targetBranchName);
//        result.put("status",responce.get(0));
//        List<String> list_ = new ArrayList<>();
//        for (int i = 1; i <responce.size() ; i++) {
//            list_.add(responce.get(i));
//        }
//        result.put("status",responce.get(0));
//        result.put("List",list_);
//        return result.toString();
//    }









     /*
     * pull branch
     * */
//    @ResponseBody
//    @RequestMapping(value ="/pull/{agentName}/{remoteAgentName}/{remoteRepoName}/{branchName}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//    public String pull(@PathVariable("agentName") String agentName,
//                           @PathVariable("remoteAgentName") String remoteAgentName,
//                           @PathVariable("remoteRepoName") String remoteRepoName,
//                            @PathVariable("branchName") String branchName
//
//
//    ) throws GitAPIException, IOException {
//        JSONObject result = new JSONObject();
//        Git repository = repositoryService.loadLocalRepository(localPath, agentName, remoteRepoName);
//        branchService.pull(branchName,repository,localPath + File.separator + remoteAgentName + File.separator + remoteRepoName);
//
//        result.put("status", "pull: " + remoteRepoName);
//        return result.toJSONString();
//    }


}
