package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agent {
    private String agentName;

    private String password;
}




