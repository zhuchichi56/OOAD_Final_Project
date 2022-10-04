package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Branch {
    private int agentId;
    private String repoName;
    private String branchName = "main";
    private int rootRepoId;
    private int currentRepoId;

    public Branch(int agentId, String repoName, int rootRepoId, int currentRepoId) {
        this.agentId = agentId;
        this.repoName = repoName;
        this.rootRepoId = rootRepoId;
        this.currentRepoId = currentRepoId;
    }
}

