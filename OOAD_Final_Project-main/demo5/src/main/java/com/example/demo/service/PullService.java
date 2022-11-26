package com.example.demo.service;

import com.example.demo.entity.PullRequest;

import java.util.List;


public interface PullService {
    int createNewPull(PullRequest pullRequest);
    List<PullRequest> getAllPull(String repositoryId);
    int rejectPull(PullRequest pullRequest);
}


