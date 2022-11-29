package com.example.demo.service.imp;

import com.example.demo.entity.Comment;
import com.example.demo.entity.Issue;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.IssueMapper;
import com.example.demo.service.IssueService;
import com.example.demo.util.DateParser;
import com.example.demo.util.encodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IssueServiceImp implements IssueService {
    @Autowired
    IssueMapper issueMapper;
    @Autowired
    CommentMapper commentMapper;

    @Override
    public int createIssueInRepo(String agentName, String repoId, String title, int isClose) {
        String issueID = encodeUtil.hash(agentName, repoId, title);
        String time = DateParser.getCurrentDate();
        return issueMapper.createIssue(issueID, agentName, repoId, title, time, String.valueOf(isClose));
    }

    @Override
    public int deleteIssueInRepo(String issueId) {
        return issueMapper.deleteIssue(issueId);
    }

    @Override
    public List<Issue> showAllIssues(String repoId) {
        return issueMapper.getIssues(repoId);
    }

    @Override
    public int commentInIssue(String issueId, String agentName, String content) {
        String time = DateParser.getCurrentDate();
        String commentId = encodeUtil.hash(issueId, agentName, content, String.valueOf(System.currentTimeMillis()));
        return commentMapper.insertComment(commentId,content,agentName,issueId,time);
    }

    @Override
    public int deleteCommentInIssue(String commentId) {
        return commentMapper.deleteComment(commentId);
    }

    @Override
    public List<Comment> showAllCommentsInIssue(String issueId) {
        return commentMapper.getComments(issueId);
    }

    @Override
    public int openIssue(String issueId, int isClose) {
        return issueMapper.updateOpen(issueId, isClose);
    }
}
