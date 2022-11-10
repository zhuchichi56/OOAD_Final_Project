package com.example.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Repo {
    private String agentName;
    private String repoName;
    private int star = 0 ;
    private int fork = 0 ;
    private int authority = 1;

    public Repo(String agentName, String repoName, int authority) {
        this.agentName = agentName;
        this.repoName = repoName;
        this.authority = authority;
    }
}
