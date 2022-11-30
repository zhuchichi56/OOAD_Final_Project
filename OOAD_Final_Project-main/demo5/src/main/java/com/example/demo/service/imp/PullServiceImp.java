package com.example.demo.service.imp;

import com.example.demo.entity.PullRequest;
import com.example.demo.mapper.PullRequestMapper;
import com.example.demo.service.PullService;
import com.example.demo.util.BranchUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "repository")
public class PullServiceImp implements PullService {
    @Autowired
    PullRequestMapper pullRequestMapper;

    @Override
    @CacheEvict(value = "pullRequest", key = "#pullRequest.targetId")
    public int createNewPull(PullRequest pullRequest) {
        return pullRequestMapper.insertPullRequest(pullRequest);
    }

    @Override
    @Cacheable(value = "pullRequest", key = "#repositoryId")
    public List<PullRequest> getAllPull(String repositoryId) {
        return pullRequestMapper.getPullRequestByTarget(repositoryId);
    }

    @Override
    @CacheEvict(value = "pullRequest", key = "#pullRequest.targetId")
    public int deletePull(PullRequest pullRequest) {
        return pullRequestMapper.deletePull(pullRequest);
    }

    @Override
    public int updatePull(PullRequest pullRequest){
        return pullRequestMapper.updatePull(pullRequest);
    }




}
