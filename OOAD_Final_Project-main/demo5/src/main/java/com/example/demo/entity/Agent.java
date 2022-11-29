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



    public Agent(String agentName, String password) {

//        @NotBlank(message="姓名不能为空")
//        @Length(min = 2, max = 4, message = "name 姓名长度必须在 {min} - {max} 之间")
        this.agentName = agentName;
        this.password = password;
    }
}




