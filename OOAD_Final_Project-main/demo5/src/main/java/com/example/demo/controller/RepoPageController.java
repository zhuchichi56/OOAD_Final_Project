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








    @ResponseBody
    @RequestMapping(value ="/{agentName}/{repoName}/{branch}/{path}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String showRepoInfo(@PathVariable("agentName") String agentName,
                               @PathVariable("repoName") String repoName,
                               @PathVariable("branch") String branch,
                               @PathVariable("path") String path
    ) throws GitAPIException, IOException {
        JSONObject result = new JSONObject();
        result.put("display","list");

        Git repo = repositoryService.loadLocalRepository(localPath, agentName,repoName);
        List<String> branchNameList = BranchUtil.getAllBranch(repo);
        List<JSONObject> branchNameJson = new ArrayList<>();
        for (String branchname:branchNameList) {
            JSONObject sub = new JSONObject();
            sub.put("branchName",branchname);
            branchNameJson.add(sub);
        }
        result.put("branchList",branchNameJson);


//        branchService.switchBranch(repo,branch);

        String basepath = localPath+ File.separator+agentName+File.separator+repoName;
        String[] splitpath = path.split("_");
        for (int i = 0; i < splitpath.length; i++) {
            basepath+=File.separator;
            basepath+=splitpath[i];
        }
        System.out.println(basepath);
        File file  = new File( basepath );
        if(file.isDirectory()) {
            File[] list = file.listFiles();
            List<JSONObject> jsonObjectList = new ArrayList<>();
            assert list != null;
            for (File f : list) {
                JSONObject sub = new JSONObject();
                sub.put("type", f.isFile() ? "file" : "folder");
                sub.put("itemName", f.getName());
                sub.put("msg", "updated 1 days ago");
                jsonObjectList.add(sub);
            }
            result.put("itemList", jsonObjectList);
            result.put("fileContent", "");
        }else {
            //要王里面加才行;
            List<JSONObject> jsonObjectList = new ArrayList<>();
            JSONObject sub = new JSONObject();
            sub.put("type", "file");
            sub.put("itemName", "file");
            sub.put("msg", "updated 1 days ago");
            jsonObjectList.add(sub);
            result.put("itemList", jsonObjectList);


            //todo:文件接收格式有问题
            BufferedReader in = new BufferedReader(new FileReader(file));
            StringBuilder Content = new StringBuilder();
            String str;
            while ((str = in.readLine()) != null) {
                Content.append(str);
            }
            Content.append(str);
            result.put("fileContent", Content.toString());
        }

         //debug
        //System.out.println(result.toJSONString());
        return result.toJSONString();

    }














}
