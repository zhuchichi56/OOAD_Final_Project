package com.example.demo.controller;


//import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;


import com.example.demo.mapper.RepositoryMapper;
import com.example.demo.service.*;
import com.example.demo.util.BranchUtil;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.pegdown.PegDownProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
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
        for (int i = 1; i < splitPath.length; i++) {
            basePath.append(File.separator);
            basePath.append(splitPath[i]);
        }

//        for (String s : splitPath) {
//            basePath.append(File.separator);
//            basePath.append(s);
//        }

//        System.out.println(basePath);

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



            //找到了这些路径
            String str ;

            if(file.getName().endsWith(".md")){
                str = md2Html(basePath.toString(),null);
                result.put("fileContent", str);
                System.out.println(str);
            }
            else {
                String content = readFileByLines(String.valueOf(basePath));
                 result.put("fileContent", content);
                System.out.println(content);
            }
        }


        return result.toJSONString();

    }


    public static String readFileByLines(String fileName) {
        try {
            File file = new File(fileName); // 要读取以上路径的input。txt文件
//            System.out.println(file.getName());
            byte[] bytes = new byte[1024];
            StringBuffer sb = new StringBuffer();
            FileInputStream in = new FileInputStream(file);
            int len;
            while ((len = in.read(bytes)) != -1) {
                sb.append(new String(bytes, 0, len));
            }
            return sb.toString();

        } catch (Exception e) {
            return "";

        }


    }


    public static String md2Html(String path,@Nullable String imgaddr) throws IOException {
        String html = readFileByLines(path);
        System.out.println(html);
        PegDownProcessor pdp = new PegDownProcessor(Integer.MAX_VALUE);

        html = pdp.markdownToHtml(html);

        System.out.println(html);
        if(!StringUtils.isEmpty(imgaddr)){
            //将html中路径"assets/***" 变为 "http://localhost:4000/assets/"
            String newHtml = StringUtils.replace(html, "assets/", imgaddr);
            return newHtml;
        }
        return html;
    }
}



