package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agent {
    private int agentId;
    private String agentName;
    public Agent(int agentId) {
        this.agentId = agentId;
    }
    public Agent(String agentName) {
        this.agentName = agentName;
    }
}




