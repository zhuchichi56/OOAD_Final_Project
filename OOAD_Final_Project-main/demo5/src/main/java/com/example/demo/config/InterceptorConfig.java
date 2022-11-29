
package com.example.demo.config;
import com.example.demo.interceptor.LoginInterceptor;
import com.example.demo.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Collections;
import java.util.List;

/**
 * @author admin
 */



@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    AgentService agentService;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new LoginInterceptor(agentService))
//                //拦截的路径
//                .addPathPatterns("/**")
//                //排除登录接口
//                .excludePathPatterns("/login/signup/{username}/{password}")
//                .excludePathPatterns("/login/signin/{username}/{password}")
//                .excludePathPatterns(Collections.singletonList("/swagger-ui.html"))
//
//                //排除用户界面
//                .excludePathPatterns("/UserPage/{name}");
//
//
//
//
//        /*第二个拦截器;*/
//        registry.addInterceptor(new LoginInterceptor(agentService))
//                //拦截的路径
//                .addPathPatterns("/**")
//                //排除登录接口
//                .excludePathPatterns("/login/signup/{username}/{password}")
//                .excludePathPatterns("/login/signin/{username}/{password}")
//                .excludePathPatterns(Collections.singletonList("/swagger-ui.html"))
//
//                //排除用户界面
//                .excludePathPatterns("/UserPage/{name}");
//

//                .excludePathPatterns("/login/signin/{username}/{password}");

    }
}






