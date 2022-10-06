package com.example.demo.service;


import com.example.demo.entity.RepoContent;

import java.util.ArrayList;

public interface VCService {

    ArrayList<RepoContent> showRepoVCList(int AgentId, String RepoName, String BranchName);

    void rollBack(int OldRepoId, int AgentId, String RepoName, String BranchName);
}


