package com.example.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Repo;
import com.example.demo.mapper.RepositoryMapper;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/RepoBrowser")
public class RepoPageController {

    String localPath = "/Users/zhuhe/Desktop/Jgit";

    String testDirectory = "/Users/zhuhe/Desktop/Jgit.md";
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



    //测下文件和分支操作吧;



    @ResponseBody
    @RequestMapping(value ="/{agentName}/{repoName}/{branch}/{path}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String showRepoInfo(@PathVariable("agentName") String agentName,
                               @PathVariable("repoName") String repoName,
                               @PathVariable("branch") String branch,
                               @PathVariable("path") String path
    ){
        JSONObject result = new JSONObject();



        List<Repo> repolist  = agentService.getRepoByName(agentName);


        result.put("display","list");
        result.put("branchList",repolist);
        result.put("itemList",repolist);
        result.put("fileContent",repolist);

        System.out.println(result.toJSONString());
        return result.toJSONString();

    }




}
