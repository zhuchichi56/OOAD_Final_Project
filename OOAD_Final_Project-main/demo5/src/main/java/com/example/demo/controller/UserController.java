package com.example.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Agent;
import com.example.demo.entity.Repo;
import com.example.demo.mapper.RepositoryMapper;
import com.example.demo.service.*;
import com.fasterxml.jackson.databind.JsonSerializer;
import org.springframework.beans.factory.NamedBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
public class UserController {


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


    /*
    *
    * 用户的注册
    *
    * */


//    @ResponseBody
//    @PostMapping(value ="/rigister")
//    public String RigisterUser(@RequestParam("name") String agentName,
//                               @RequestParam("age") String password){
//        String name = agentService.createUser(new Agent(agentName,password));
//        if(name== null){
//            return "Wrong";
//        }else {
//            return "创建成功";
//        }
//    }

    /*
     *
     * 用户的登陆
     *
     * */




    /*
     *
     * 用户的注销
     *
     * */


}
