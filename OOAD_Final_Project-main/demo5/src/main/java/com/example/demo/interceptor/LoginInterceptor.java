package com.example.demo.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.mapper.AgentMapper;
import com.example.demo.service.AgentService;
import com.example.demo.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;


@Component
public class LoginInterceptor implements HandlerInterceptor {

    AgentService agentService;

    public LoginInterceptor(AgentService agentService) {
        this.agentService = agentService;
    }

    //只是验证呢个
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String token = request.getHeader("Token");
        String[] strings = JwtUtil.checkToken(token);
        if(strings==null) {
            return false;
        }else {
            return true;
        }
    }

}



