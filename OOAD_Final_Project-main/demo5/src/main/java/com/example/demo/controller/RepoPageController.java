package com.example.demo.controller;


//import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;


import com.example.demo.entity.PullRequest;
import com.example.demo.entity.Repo;
import com.example.demo.mapper.AgentMapper;
import com.example.demo.mapper.ForkRepoMapper;
import com.example.demo.mapper.RepositoryMapper;
import com.example.demo.mapper.WatchRepoMapper;
import com.example.demo.service.*;
import com.example.demo.util.BranchUtil;
import org.eclipse.jgit.annotations.Nullable;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.pegdown.PegDownProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
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
    ForkRepoMapper forkRepoMapper;

    @Autowired
    WatchRepoMapper watchRepoMapper;

    @Autowired
    AgentService agentService;

    @Autowired
    IssueService issueService;

    @Autowired
    BranchService branchService;

    @Autowired
    CommitService commitService;

    @Autowired
    AgentMapper agentMapper;

    @Autowired
    PullService pullService;






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

        /**
         *
         *
         *angentName 是仓库的主人
         *
         * */
        result.put("Fork",repositoryMapper.getFork(repositoryMapper.getRepoId(agentName,repoName)));
        result.put("canFork",repositoryMapper.forkIsValid(agentName,repositoryMapper.getRepoId(agentName,repoName)));

        result.put("Star",repositoryMapper.getStar(repositoryMapper.getRepoId(agentName,repoName)));
        result.put("canStar",repositoryMapper.starIsValid(agentName,repositoryMapper.getRepoId(agentName,repoName)));

        result.put("watch",  repositoryMapper.getWatch(repositoryMapper.getRepoId(agentName,repoName)));
        result.put("canWatch", repositoryMapper.watchIsValid(agentName,repositoryMapper.getRepoId(agentName,repoName)));

        result.put("Auth", repositoryMapper.getRepoById(repositoryMapper.getRepoId(agentName,repoName)).getAuthority());
        result.put("PRList", getAllPR(repositoryMapper.getRepoId(agentName,repoName)));
        /**
         *
         *
         *
         * */

        result.put("branchList",branchNameJson);
        result.put("contributorsList",agentService.getContributors(repositoryMapper.getRepoId(agentName, repoName)));


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

        System.out.println(result.toJSONString());
        return result.toJSONString();


    }



    public List<JSONObject> getAllPR(String RepoId){
        List<PullRequest> pullRequestList = pullService.getAllPull(RepoId);
        List<JSONObject> pulljsonlist = new ArrayList<>();
        for (PullRequest pull:pullRequestList) {
            JSONObject sub = new JSONObject();
            Repo targetRepo = repositoryMapper.getRepoById(pull.getTargetId());
            Repo repoRepo = repositoryMapper.getRepoById(pull.getRepositoryId());

            sub.put("PRTitle",pull.getTitle());

            sub.put("status",pull.getIsClosed()==1?"closed":"open");
            sub.put("result",pull.getStatus());
            sub.put("target",targetRepo.getAgentName());
            sub.put("targetRepo",targetRepo.getRepoName());
            sub.put("targetBranch",pull.getTargetBranch());
            sub.put("from",repoRepo.getAgentName());
            sub.put("fromRepo",repoRepo.getRepoName());
            sub.put("fromBranch",pull.getBranch());
            pulljsonlist.add(sub);
        }
        return pulljsonlist;
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






    /**
     *
     * Contributor;
     *
     *
     * */


    /*
    * 还要验证这个用户是否存在,如果不存在就返回0;
    * */



    @GetMapping("/AddContributer/{userName}/{repoName}/{contributerName}")
    public int inviteContributor(
            @PathVariable("userName") String userName,
            @PathVariable("repoName") String repoName,
            @PathVariable("contributerName") String contributerName
    ) {

        if(!agentMapper.existUser(contributerName)){
            return 0;
        }
        else {
            return agentService.inviteContributor(contributerName, repositoryMapper.getRepoId(userName, repoName));
        }
    }

    @GetMapping("/removeContributer/{User}/{repoName}/{contributerName}")
    public int removeContributor(
            @PathVariable("User") String User,
            @PathVariable("repoName") String repoName,
            @PathVariable("contributerName") String contributerName
    ) {
        return agentService.removeContributor(contributerName, repositoryMapper.getRepoId(User, repoName));
    }









}









