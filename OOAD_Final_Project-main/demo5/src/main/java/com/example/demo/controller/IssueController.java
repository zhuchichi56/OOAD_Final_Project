//package com.example.demo.controller;
//
//
//import com.alibaba.fastjson.JSONObject;
//import com.example.demo.entity.Comment;
//import com.example.demo.entity.Issue;
//import com.example.demo.service.IssueService;
//import com.google.gson.Gson;
//import org.eclipse.jgit.api.Git;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping(value = "/Issue")
//public class IssueController {
//
//    @Autowired
//    IssueService issueService;
//
//    @ResponseBody
//    @RequestMapping(value ="/createIssue/{agentName}/{repoId}/{title}/{isClose}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//    public String createIssue(@PathVariable("agentName") String agentName,
//                            @PathVariable("repoId") String repoId,
//                            @PathVariable("title") String title,
//                              @PathVariable("isClose") String isClose
//
//    ) {
//        JSONObject result = new JSONObject();
//        int status = issueService.createIssueInRepo(agentName, repoId, title, Integer.parseInt(isClose));
//        if(status == 1) result.put("status", 1);
//        else result.put("status", 0);
//        return result.toJSONString();
//    }
//
//
//    @ResponseBody
//    @RequestMapping(value ="/deleteIssue/{issueId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//    public String deleteIssue(@PathVariable("issueId") String issueId
//    ) {
//        JSONObject result = new JSONObject();
//        int status = issueService.deleteIssueInRepo(issueId);
//        if(status == 1) result.put("status", 1);
//        else result.put("status", 0);
//        return result.toJSONString();
//    }
//
//
//    @ResponseBody
//    @RequestMapping(value ="/commentIssue/{issueId}/{agentName}/{content}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//    public String commentIssue(@PathVariable("issueId") String issueId,
//                              @PathVariable("agentName") String agentName,
//                              @PathVariable("content") String content
//    ) {
//        JSONObject result = new JSONObject();
//        int status = issueService.commentInIssue(issueId, agentName, content);
//        if(status == 1) result.put("status", 1);
//        else result.put("status", 0);
//        return result.toJSONString();
//    }
//
//
//    @ResponseBody
//    @RequestMapping(value ="/showAllIssues/{repoId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//    public String showAllIssues(@PathVariable("repoId") String repoId
//    ) {
//
//        List<Issue> issues = issueService.showAllIssues(repoId);
//        Gson gson = new Gson();
//        String issuesJson = gson.toJson(issues);
//
//        return issuesJson;
//    }
//
//    @ResponseBody
//    @RequestMapping(value ="/deleteComment/{commentId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//    public String deleteComment(@PathVariable("commentId") String commentId
//    ) {
//        JSONObject result = new JSONObject();
//        int status = issueService.deleteCommentInIssue(commentId);
//        if(status == 1) result.put("status", 1);
//        else result.put("status", 0);
//        return result.toJSONString();
//    }
//
//
//    @ResponseBody
//    @RequestMapping(value ="/showAllComments/{issueId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//    public String showAllComments(@PathVariable("issueId") String issueId
//    ) {
//
//        List<Comment> issues = issueService.showAllCommentsInIssue(issueId);
//        Gson gson = new Gson();
//        String commentsJson = gson.toJson(issues);
//
//        return commentsJson;
//    }
//
//    @ResponseBody
//    @RequestMapping(value ="/closeIssue/{issueId}/{isClose}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//    public String closeIssue(@PathVariable("issueId") String issueId,
//                              @PathVariable("isClose") String isClose
//
//
//    ) {
//        JSONObject result = new JSONObject();
//        int status = issueService.openIssue(issueId, Integer.parseInt(isClose));
//
//        if(status == 1) result.put("status", 1);
//        else result.put("status", 0);
//        return result.toJSONString();
//    }
//
//}
