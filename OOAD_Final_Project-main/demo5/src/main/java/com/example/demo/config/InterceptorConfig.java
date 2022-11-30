
package com.example.demo.config;
import com.example.demo.interceptor.*;
import com.example.demo.mapper.RepositoryMapper;
import com.example.demo.service.AgentService;
import com.example.demo.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * @author admin
 */



@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    AgentService agentService;
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    RepositoryMapper repositoryMapper;

    @Resource
    private CorsInterceptor corsInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {



        registry.addInterceptor(corsInterceptor).addPathPatterns("/**");
//
//
        /*
        * 第一层访问
        * 验证有没有token,验证用户是否登陆了
        * */
//        registry.addInterceptor(new LoginInterceptor(agentService))
//                //拦截的路径
//                .addPathPatterns("/**")
//                //排除登录接口
//                .excludePathPatterns("/login/signup/{username}/{password}")
//                .excludePathPatterns("/")
//                .excludePathPatterns("/login/signin/{username}/{password}")
//                .excludePathPatterns("/UserPage/{name}")
//                .excludePathPatterns("/swagger-ui.html");





        /*
         * UserPage访问
         * 判断是不是你自己决定访问权限和编辑权限
         * */
        registry.addInterceptor(new UserInterceptor())
                //拦截的路径
                .addPathPatterns("/UserPage/{name}/**")
                .addPathPatterns("/Repository/**")
                .addPathPatterns("/RepoBrowser/AddContributer/{userName}/{repoName}/{contributerName}")
                .addPathPatterns("/RepoBrowser/removeContributer/{User}/{repoName}/{contributerName}")
                .addPathPatterns("/Branch/**")
                .addPathPatterns("/PR/**")





                //排除登录接口
                .excludePathPatterns("/login/signup/{username}/{password}")
                .excludePathPatterns("/Issue/**")
                .excludePathPatterns("/PR/fork/**")
                .excludePathPatterns("/RepoBrowser/{agentName}/{repoName}/{branchName}/getCommitList")
                .excludePathPatterns("/login/signin/{username}/{password}")
                .excludePathPatterns("/UserPage/{name}")
                .excludePathPatterns("/swagger-ui.html");


        /*
         * RepoPage访问
         * 判断仓库能不能进入
         * */
        registry.addInterceptor(new RBFinderInterceptor(agentService,repositoryService,repositoryMapper))
                //拦截的路径
                .addPathPatterns("/RepoBrowser/{agentName}/{repoName}/{branch}/{path}")
                .addPathPatterns("/RepoBrowser/{agentName}/{repoName}/{branch}/{path}/download")
                .addPathPatterns("/RepoBrowser/{agentName}/{repoName}/{branch}/{path}/Rollback")
                .addPathPatterns("/watch/add/{repoOwner}/{repoName}/{agentName}")
                .addPathPatterns("/star/add/{repoOwner}/{repoName}/{agentName}")
                .addPathPatterns("/star/delete/{repoOwner}/{repoName}/{agentName}")
                .addPathPatterns("/watch/delete/{repoOwner}/{repoName}/{agentName}")
                .addPathPatterns("/PR/fork/**");




        registry.addInterceptor(new RBFixInterceptor(agentService,repositoryService,repositoryMapper))
                //拦截的路径
                .addPathPatterns("/RepoBrowser/{agentName}/{repoName}/{branchName}/{path}/upload/{token_name}");







        /*
        * 这个是只有自己才有权限的
        * */
//        registry.addInterceptor(new BranchInterceptor())
//                //拦截的路径
//                .addPathPatterns("/Branch/**")
//                .addPathPatterns("/PR/**")
//                //这个要考虑一下fork操作;
//                .addPathPatterns("/Repository/delete/**")
//                .addPathPatterns("/Repository/init/**")
//
//                //排除登录接口
//                .excludePathPatterns("/login/signup/{username}/{password}")
//                .excludePathPatterns("/login/signin/{username}/{password}")
//                .excludePathPatterns("/UserPage/{name}")
//                .excludePathPatterns("/swagger-ui.html");




    }
}






