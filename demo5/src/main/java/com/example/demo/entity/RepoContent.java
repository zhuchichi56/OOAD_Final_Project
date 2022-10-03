package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RepoContent {
    private int repoId;
    private String content;
    private String comment;
    private String commitTime;
    private String versionId;
}
