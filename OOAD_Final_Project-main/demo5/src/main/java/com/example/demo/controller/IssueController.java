package com.example.demo.controller;


import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.Comment;
import com.example.demo.entity.Issue;
import com.example.demo.mapper.RepositoryMapper;
import com.example.demo.service.IssueService;
import com.google.gson.Gson;
import org.eclipse.jgit.api.Git;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/Issue")
public class IssueController {

    @Autowired
    IssueService issueService;

    @Autowired
    RepositoryMapper repositoryMapper;

    @ResponseBody
    @RequestMapping(value ="/createIssue/{agentName}/{ownerName}/{repoName}/{title}/{isClose}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String createIssue(@PathVariable("agentName") String agentName,
                            @PathVariable("ownerName") String ownerName,
                            @PathVariable("repoName") String repoName,
                            @PathVariable("title") String title,
                              @PathVariable("isClose") String isClose

    ) {
        JSONObject result = new JSONObject();
        String repoId = repositoryMapper.getRepoId(ownerName, repoName);
        int status = issueService.createIssueInRepo(agentName, repoId, title, Integer.parseInt(isClose));
        if(status == 1) result.put("status", 1);
        else result.put("status", 0);
        return result.toJSONString();
    }


    @ResponseBody
    @RequestMapping(value ="/deleteIssue/{issueId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String deleteIssue(@PathVariable("issueId") String issueId


    ) {
        JSONObject result = new JSONObject();
        int status = issueService.deleteIssueInRepo(issueId);
        if(status == 1) result.put("status", 1);
        else result.put("status", 0);
        return result.toJSONString();
    }


    @ResponseBody
    @RequestMapping(value ="/commentIssue/{issueId}/{agentName}/{content}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String commentIssue(@PathVariable("issueId") String issueId,
                              @PathVariable("agentName") String agentName,
                              @PathVariable("content") String content

    ) {
        JSONObject result = new JSONObject();
        int status = issueService.commentInIssue(issueId, agentName, content);
        if(status == 1) result.put("status", 1);
        else result.put("status", 0);
        return result.toJSONString();
    }


    @ResponseBody
    @RequestMapping(value ="/showAllIssues/{ownerName}/{repoName}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String showAllIssues(@PathVariable("ownerName") String ownerName,
                                @PathVariable("repoName") String repoName
    ) {
        String repoId = repositoryMapper.getRepoId(ownerName, repoName);
        List<Issue> issues = issueService.showAllIssues(repoId);
        for(Issue issue:issues){
            List<Comment> comments = issueService.showAllCommentsInIssue(issue.getIssueId());
            issue.setContent(comments);
        }
        Map<String, List<Issue>> result = new HashMap<>();
        result.put("issues", issues);
        Gson gson = new Gson();

        return gson.toJson(result);
    }

    @ResponseBody
    @RequestMapping(value ="/deleteComment/{commentId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String deleteComment(@PathVariable("commentId") String commentId
    ) {
        JSONObject result = new JSONObject();
        int status = issueService.deleteCommentInIssue(commentId);
        if(status == 1) result.put("status", 1);
        else result.put("status", 0);
        return result.toJSONString();
    }


    @ResponseBody
    @RequestMapping(value ="/showAllComments/{issueId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String showAllComments(@PathVariable("issueId") String issueId
    ) {

        List<Comment> comments = issueService.showAllCommentsInIssue(issueId);
        Gson gson = new Gson();
        Map<String, List<Comment>> result = new HashMap<>();
        result.put("comments", comments);

        return gson.toJson(result);
    }

    @ResponseBody
    @RequestMapping(value ="/openIssue/{issueId}/{isClose}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String openIssue(@PathVariable("issueId") String issueId,
                              @PathVariable("isClose") String isClose


    ) {
        JSONObject result = new JSONObject();
        int status = issueService.openIssue(issueId, Integer.parseInt(isClose));

        if(status == 1) result.put("status", 1);
        else result.put("status", 0);
        return result.toJSONString();
    }

}

