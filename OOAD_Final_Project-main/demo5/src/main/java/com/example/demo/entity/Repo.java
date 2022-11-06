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

    public Repo(String agentName, String repoName) {
        this.agentName = agentName;
        this.repoName = repoName;
    }
}
