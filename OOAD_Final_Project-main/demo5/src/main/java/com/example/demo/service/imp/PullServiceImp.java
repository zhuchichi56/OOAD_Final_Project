package com.example.demo.service.imp;

import com.example.demo.entity.PullRequest;
import com.example.demo.mapper.PullRequestMapper;
import com.example.demo.service.PullService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PullServiceImp implements PullService {
    @Autowired
    PullRequestMapper pullRequestMapper;

    @Override
    public int createNewPull(PullRequest pullRequest) {
        return pullRequestMapper.insertPullRequest(pullRequest);
    }

    @Override
    public List<PullRequest> getAllPull(String repositoryId) {
        return pullRequestMapper.getPullRequestByTarget(repositoryId);
    }


    @Override
    public int rejectPull(PullRequest pullRequest) {
        return pullRequestMapper.rejectPull(pullRequest.getRepositoryId(), pullRequest.getAgentName(), pullRequest.getBranch());
    }
}
