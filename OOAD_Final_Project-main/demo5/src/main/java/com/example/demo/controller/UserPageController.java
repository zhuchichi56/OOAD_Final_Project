package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Repo;
import com.example.demo.mapper.RepositoryMapper;
import com.example.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.List;


@RestController
@RequestMapping(value = "/UserPage")
public class UserPageController {



    /***
     * json:
     *  userName:
     *  repoList:
     *      repoName:
     *      permission:
     *      msg:
     *  useImage:
     */

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





    @ResponseBody
    @RequestMapping(value ="/{name}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String showUserInfo(@PathVariable("name") String name){

        JSONObject result = new JSONObject();
        List<Repo> repolist  = agentService.getRepoByName(name);
        result.put("userName",name);
        result.put("repoList",repolist);
        result.put("userImg", "@Image('900x900','@color', 'Joey')");
        System.out.println(result.toJSONString());
        return result.toJSONString();

    }



}

