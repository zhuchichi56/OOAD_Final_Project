package com.example.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaticRepo {
    private int agentId;
    private String repoName;
    private int star = 0 ;
    private int fork = 0 ;

    public StaticRepo(int agentId, String repoName) {
        this.agentId = agentId;
        this.repoName = repoName;
    }
}
