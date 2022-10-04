package com.example.demo.service;


import com.example.demo.entity.RepoContent;

import java.util.ArrayList;

public interface VCService {

    ArrayList<RepoContent> showRepoCVList(int AgentId, String RepoName, String BranchName);
}


