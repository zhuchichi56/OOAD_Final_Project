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
    private int version_id;
    private String content;
    private String comment;
    private String commit_time;

    public repository(int repoId, int version_id, String content, String comment, String commit_time) {
        this.repoId = repoId;
        this.version_id = version_id;
        this.content = content;
        this.comment = comment;
        this.commit_time = commit_time;
    }
}
