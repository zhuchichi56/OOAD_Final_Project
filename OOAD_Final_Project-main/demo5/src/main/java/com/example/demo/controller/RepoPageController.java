package com.example.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Repo;
import com.example.demo.mapper.RepositoryMapper;
import com.example.demo.service.*;
import com.example.demo.util.BranchUtil;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/RepoBrowser")
public class RepoPageController {

    String localPath = "C:/Users/vip/Desktop/TEST/Local";

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
    @RequestMapping(value ="/{agentName}/{repoName}/{branch}/{path}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String showRepoInfo(@PathVariable("agentName") String agentName,
                               @PathVariable("repoName") String repoName,
                               @PathVariable("branch") String branch,
                               @PathVariable("path") String path
    ) throws GitAPIException, IOException {
        JSONObject result = new JSONObject();
        result.put("display","list");

        Git repo = repositoryService.loadLocalRepository(localPath, agentName, repoName);
        List<String> branchNameList = BranchUtil.getAllBranch(repo);

        List<JSONObject> branchNameJson = new ArrayList<>();
        for (String branchName:branchNameList) {
            JSONObject sub = new JSONObject();
            sub.put("branchName",branchName);
            branchNameJson.add(sub);
        }

        result.put("branchList",branchNameJson);


        branchService.switchBranch(repo,branch);
        //localPath / name / repo
        StringBuilder basePath = new StringBuilder(localPath + File.separator + agentName + File.separator + repoName);
        String[] splitPath = path.split("_");
        //convert the path
        for (String s : splitPath) {
            basePath.append(File.separator);
            basePath.append(s);
        }

        System.out.println(basePath);

        File file  = new File(basePath.toString());

        if(file.isDirectory()) {
            File[] list = file.listFiles();
            List<JSONObject> jsonObjectList = new ArrayList<>();
            assert list != null;
            for (File f : list) {
                JSONObject sub = new JSONObject();
                sub.put("type", f.isFile() ? "file" : "folder");
                sub.put("itemName", f.getName());
                sub.put("msg", "last modified: " + new Date(f.lastModified()));
                jsonObjectList.add(sub);
            }
            result.put("itemList", jsonObjectList);
            result.put("fileContent", "");
        }else {

            List<JSONObject> jsonObjectList = new ArrayList<>();
            JSONObject sub = new JSONObject();
            sub.put("type", "file");
            sub.put("itemName", "file");
            sub.put("msg", "last modified: " + new Date(file.lastModified()));
            jsonObjectList.add(sub);
            result.put("itemList", jsonObjectList);

            BufferedReader in = new BufferedReader(new FileReader(file));
            StringBuilder Content = new StringBuilder();
            String str;
            while ((str = in.readLine()) != null) {
                Content.append(str);
            }


            Content.append(str);
            result.put("fileContent", Content.toString());
        }


        return result.toJSONString();

    }




}
