package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Agent;
import com.example.demo.service.AgentService;
import com.example.demo.service.BranchService;
import com.example.demo.service.CommitService;
import com.example.demo.service.RepositoryService;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {
    String localPath = "/Users/zhuhe/Desktop/Jgit";

    @Autowired
    RepositoryService repositoryService;
    @Autowired
    BranchService branchService;
    @Autowired
    CommitService commitService;

    @Autowired
    AgentService agentService;


    @GetMapping("/login/signup/{username}/{password}")
    public int Signup(@PathVariable("username") String agentName,
                      @PathVariable("password") String repoName) {
        int a = agentService.createUser(new Agent(agentName, repoName));
        if (a == 0) {
            System.out.println("已经注册");
        } else {
            System.out.println("注册成功");
        }
        return a;
    }




    @GetMapping("/login/signin/{username}/{password}")
    public String  SignIn(@PathVariable("username") String agentName,
                       @PathVariable("password") String password) {

        int a = agentService.CheckUser(agentName,password);
        if(a==0){
            System.out.println("请先登录");
        }else {
            System.out.println("登陆成功");
        }
        String token  = JwtUtil.createToken(agentName,password);
        JSONObject result = new JSONObject();
        result.put("Login", a);
        result.put("Token", token);
        System.out.println(token);
        return result.toJSONString();
    }

}
