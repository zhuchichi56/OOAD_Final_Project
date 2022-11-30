package com.example.demo.interceptor;

import com.example.demo.service.AgentService;
import com.example.demo.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;


@Component
public class UserInterceptor implements HandlerInterceptor {





    //这个只验证是不是自己;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String url = request.getRequestURI();
        String [] strings = url.split("/");
        String token = request.getHeader("Token");
        if(token==null) {
            return false;
        }
        String currentAgentName = JwtUtil.checkToken(token)[0];
        System.out.println(currentAgentName);
        for (int i = 0; i < strings.length; i++) {
            if(strings[i].equals(currentAgentName)){
                System.out.println("这是用户本人,验证通过");
                return true;
            }
        }
        System.out.println("这不是用户本人,验证失败");
        return JwtUtil.sendWrongResponce(response,"您没有编辑权限!");
    }



}



