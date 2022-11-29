package com.example.demo.service;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Issue;

import java.util.List;

public interface IssueService {

    int createIssueInRepo(String agentName,String repoId,String title, int isClose);

    int deleteIssueInRepo(String issueId);

    List<Issue> showAllIssues(String repoId);

    int commentInIssue(String issueId, String agentName, String content);

    int deleteCommentInIssue(String commentId);

    List<Comment> showAllCommentsInIssue(String issueId);

    int openIssue(String issueId, int isClose);
}
