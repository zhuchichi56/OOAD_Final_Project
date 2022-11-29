package com.example.demo.interceptor;

import com.example.demo.service.AgentService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 这个是拦截以repoBrowser开头的所有请求
 * */


@Component
public class RepoBrowserInterceptor implements HandlerInterceptor {


    AgentService agentService;

    public RepoBrowserInterceptor(AgentService agentService) {
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




