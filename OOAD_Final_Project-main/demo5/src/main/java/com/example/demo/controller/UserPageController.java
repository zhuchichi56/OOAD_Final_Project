package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Agent;
import com.example.demo.entity.Repo;
import com.example.demo.mapper.RepositoryMapper;
import com.example.demo.service.*;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@RestController
@RequestMapping(value = "/UserPage")
public class UserPageController {


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

        List<Repo> starRepo = agentService.getStarRepos(name);



//        ArrayList<JSONObject> arrayList = new ArrayList<>();
//        for (int i = 0; i < repolist.size(); i++) {
//            Repo repo =repolist.get(i);
//            JSONObject sub = new JSONObject();
//            sub.put("agentName",repo.getAgentName());
//            sub.put("authority",repo.getAuthority());
//
//
//            sub.put("fork");
//            sub.put("ownerRepoId",repo.getAgentName());
//            sub.put("repoName",repo.getAgentName());
//            sub.put("star",repo.getAgentName());
//
//
//        }
        result.put("repoList",repolist);



        result.put("userImg", String.format("@Image('900x900','@color', '%s')", name));
        System.out.println(result.toJSONString());
        return result.toJSONString();

    }



    @ResponseBody
    @RequestMapping(value ="/{name}/edit/{newName}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String EditUserName(@PathVariable("name") String name, @PathVariable("newName") String latest){
        JSONObject result = new JSONObject();
        int success = agentService.updateUserName(name, latest);
        if (success == 1){
            result.put("status", "success");
        } else {
            result.put("status", "The name has existed");
        }
        System.out.println(result.toJSONString());
        return result.toJSONString();
    }


    @ResponseBody
    @RequestMapping(value ="/{name}/edit/{password}/{newPassword}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String EditUserName(@PathVariable("name") String name,
                               @PathVariable("password") String oldPassword, @PathVariable("newPassword") String password){
        JSONObject result = new JSONObject();
        int success = agentService.updateUserPassword(name, oldPassword, password);
        if (success == 1){
            result.put("status", "success");
        } else {
            result.put("status", "the old password is wrong");
        }
        System.out.println(result.toJSONString());
        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value ="/{name}/edit/logout", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String DeleteUser(@PathVariable("name") String name){
        JSONObject result = new JSONObject();
        agentService.deleteUser(localPath, name);
        result.put("status", "success");
        System.out.println(result.toJSONString());
        return result.toJSONString();
    }


//
//    @GetMapping("/{name}/checkToken")
//    public int checkToken(HttpServletRequest request){
//        String token = request.getHeader("Token");
////        System.out.println(token);
//        String[] strings = JwtUtil.checkToken(token);
//       /*
//       没有token返回报错;
//       */
//
//        if(strings==null){
//            return 0;
//        }
//        System.out.println(Arrays.toString(strings));
//
//        /*
//        如果token出错也不行;
//       */
//
//        return agentService.CheckUser(strings[0],strings[1]);
//    }






}

