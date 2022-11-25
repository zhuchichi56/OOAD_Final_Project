package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.awt.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Agent {
    private String agentName;

    private String password;

    private String iconUrl = "C:\\Users\\12078\\Desktop\\大三上\\picture\\default_icon.png";
}




