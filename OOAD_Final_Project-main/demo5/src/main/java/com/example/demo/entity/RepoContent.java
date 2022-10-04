package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepoContent {
    private int repoId;
    private String content = "MarkDown.md";
    private String comment = "Create a New Repo";
    private String commitTime = "2002-11-1";



    public RepoContent(String content, String comment, String commitTime) {
        this.content = content;
        this.comment = comment;
        this.commitTime = commitTime;
    }


}
