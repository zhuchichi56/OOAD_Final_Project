package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PullRequest implements Serializable {
    String repositoryId;
    String agentName;
    String branch;
    String targetId;
    String targetBranch;
    int isClosed = 0 ;
    String title;
    int status = 0 ;
}
