package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class repository {
    private int repoId;
    private String name;
    private int star;
    private int fork;
    private int AgentId;
    private int versionId;
    private String branch;
    private String content;
    private String comment;
    private String commit_time;

    public repository(String name, int star, int fork, int agentId, int versionId, String branch, String content, String comment, String commit_time) {
        this.name = name;
        this.star = star;
        this.fork = fork;
        AgentId = agentId;
        this.versionId = versionId;
        this.branch = branch;
        this.content = content;
        this.comment = comment;
        this.commit_time = commit_time;
    }
}
