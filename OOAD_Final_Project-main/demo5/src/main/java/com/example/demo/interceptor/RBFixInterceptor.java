package com.example.demo.interceptor;

import com.example.demo.entity.Repo;
import com.example.demo.mapper.RepositoryMapper;
import com.example.demo.service.AgentService;
import com.example.demo.service.RepositoryService;
import com.example.demo.util.JwtUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;


/*
* 这个获得了仓库的访问界面;
* */
@Component
public class RBFixInterceptor implements HandlerInterceptor {


    AgentService agentService;

    RepositoryService repositoryService;

    RepositoryMapper repositoryMapper;

    public RBFixInterceptor(AgentService agentService, RepositoryService repositoryService, RepositoryMapper repositoryMapper) {
        this.agentService = agentService;
        this.repositoryService = repositoryService;
        this.repositoryMapper = repositoryMapper;
    }




    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        String url_ = request.getRequestURI();
        String url = url_.substring(1);
        String [] strings = url.split("/");

        //这个是拿到url就行了;
        String currentAgentName =null;
        String currRepoName =null;
        String myName = null;
        System.out.println(strings[0]);
        System.out.println(strings[0]);
        if(Objects.equals(strings[0], "RepoBrowser")){
            currentAgentName = strings[1]; //agentName
            System.out.println(currentAgentName);
            currRepoName = strings[2];//RepoName
            System.out.println(currRepoName);
            myName = strings[6];
        }

        /*判断是不是本人*/
        for (int i = 0; i < strings.length; i++) {
            if(strings[i].equals(myName)){
                System.out.println("这是用户本人,验证通过");
                return true;
            }
        }


        int aut = 0 ;
        int isContributor = 0 ;
        try {
            String currPepoId = repositoryService.getRepoId(currentAgentName, currRepoName);
            Repo currRepo = repositoryMapper.getRepoById(currPepoId);
            aut = currRepo.getAuthority();
            System.out.println(myName);
            System.out.println(currPepoId);
            isContributor = agentService.checkContributor(myName, currPepoId);
        }catch (Exception e){}
        //执行第二次判断;
        if (aut == 1) {
            if(isContributor==1){
                return true;
            }else {
                return JwtUtil.sendWrongResponce(response,"您不是contributor!");
            }
        } else {
            return JwtUtil.sendWrongResponce(response,"您没有权限访问此仓库");
        }

    }



}





