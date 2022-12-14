package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.service.BranchService;
import com.example.demo.service.CommitService;
import com.example.demo.service.RepositoryService;
import com.example.demo.util.DateParser;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PRController {


    String localPath = "/Users/zhuhe/Desktop/Jgit";

    @Autowired
    RepositoryService repositoryService;
    @Autowired
    BranchService branchService;
    @Autowired
    CommitService commitService;


    @ResponseBody
    @RequestMapping(value ="/fork/{targetagentname}/{targetrepoName}/{targetbranch}/{ownername}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String fork(         @PathVariable("agentName") String agentName,
                                @PathVariable("repoName") String repoName,
                                @PathVariable("branchName") String branchName

    ) {

        JSONObject result = new JSONObject();
        List<RevCommit> commitlist = commitService.getCommitsByBranch(localPath,agentName,repoName,branchName);
        List<JSONObject> cljson  = new ArrayList<>();
        for (RevCommit revCommit: commitlist){
            JSONObject sub = new JSONObject();
            sub.put("commitUser", DateParser.getCommitDate(revCommit.getCommitTime()));
            sub.put("time",DateParser.getCommitDate(revCommit.getCommitTime()));
            sub.put("commitId", revCommit.getName());
            cljson.add(sub);
        }
        result.put("commitList",cljson);
        return result.toJSONString();
    }













}
