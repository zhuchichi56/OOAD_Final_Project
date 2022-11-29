package com.example.demo.interceptor;

import com.example.demo.mapper.AgentMapper;
import com.example.demo.service.AgentService;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Arrays;


@Component
public class LoginInterceptor implements HandlerInterceptor {

    AgentService agentService;

    public LoginInterceptor(AgentService agentService) {
        this.agentService = agentService;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        String token = request.getHeader("Token");
//
//
//        System.out.println(token);
//        String[] strings = JwtUtil.checkToken(token);
//        if(strings==null) {
//            return false;
//        }
//        System.out.println(Arrays.toString(strings));
//
//
//        return agentService.CheckUser(strings[0], strings[1])==1;
        return true;

    }
}



