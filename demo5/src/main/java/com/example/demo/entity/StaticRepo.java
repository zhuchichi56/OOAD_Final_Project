package com.example.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StaticRepo {
    private int repoId;
    private String repoName;
    private int star;
    private int fork;
    private int agentId;
}
