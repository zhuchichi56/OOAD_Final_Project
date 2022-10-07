package com.example.demo.service;

import com.example.demo.entity.RepoContent;

import java.util.ArrayList;

public interface VCService {

    ArrayList<RepoContent> showRepoVCList(int agentId, String repoName, String branchName);

    void rollBack(int oldRepoId, int agentId, String repoName, String branchName);
}


