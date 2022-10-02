package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Repository {
    private int repoId;
    private String name;
    private int star;
    private int fork;
    private int agentId;
    private int versionId;
    private String branch;
    private String content;
    private String comment;
    private String commitTime;

    public Repository(String name, int star, int fork, int agentId, int versionId, String branch, String content, String comment, String commitTime) {
        this.name = name;
        this.star = star;
        this.fork = fork;
        this.agentId = agentId;
        this.versionId = versionId;
        this.branch = branch;
        this.content = content;
        this.comment = comment;
        this.commitTime = commitTime;
    }
}
