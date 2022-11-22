package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.RepositoryService;
import com.example.demo.util.BranchUtil;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/Repository")
public class RepositoryController {
    String localPath = "C:/Users/vip/Desktop/TEST/Local";
    @Autowired

    RepositoryService repositoryService;



    @ResponseBody
    @RequestMapping(value ="/init/{agentName}/{repoName}/{authority}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String initRepo(@PathVariable("agentName") String agentName,
                               @PathVariable("repoName") String repoName,
                               @PathVariable("authority") String authority

    ) {
        JSONObject result = new JSONObject();
        Git repository = repositoryService.initRepository(localPath, agentName, repoName, Integer.parseInt(authority));

        result.put("status", "New repository successfully created");
        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value ="/clone/{agentName}/{remoteAgentName}/{remoteRepoName}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String cloneRepo(@PathVariable("agentName") String agentName,
                               @PathVariable("remoteAgentName") String remoteAgentName,
                            @PathVariable("remoteRepoName") String remoteRepoName

    ) {
        JSONObject result = new JSONObject();
        Git repository = repositoryService.cloneRepository(localPath + File.separator + remoteAgentName + File.separator + remoteRepoName, localPath, agentName, remoteRepoName);
        result.put("status", "New repository successfully cloned");
        return result.toJSONString();
    }


    //deleteRepository(String path, String agentName ,String repositoryName)
    @ResponseBody
    @RequestMapping(value ="/deleteRepository/{agentName}/{repoName}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String deleteRepo(@PathVariable("agentName") String agentName,
                            @PathVariable("repoName") String repoName

    ) {
        JSONObject result = new JSONObject();
        boolean status = repositoryService.deleteRepository(localPath, agentName, repoName);
        if(status) result.put("status", "Successfully delete");else result.put("status", "Unsuccessfully delete");

        return result.toJSONString();
    }


}
