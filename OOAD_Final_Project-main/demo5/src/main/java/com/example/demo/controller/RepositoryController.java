package com.example.demo.controller;
import com.example.demo.service.RepositoryService;
import org.eclipse.jgit.api.Git;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping(value = "/Repository")
public class RepositoryController {
    String localPath = "/Users/zhuhe/Desktop/Jgit";
    @Autowired

    RepositoryService repositoryService;


    /**
     * 仓库的创建和初始化
    * */
    @ResponseBody
    @RequestMapping(value ="/init/{agentName}/{repoName}/{authority}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public int initRepo(@PathVariable("agentName") String agentName,
                               @PathVariable("repoName") String repoName,
                               @PathVariable("authority") String authority

    ) {
        Git repository = repositoryService.initRepository(localPath, agentName, repoName, Integer.parseInt(authority));
       return repository==null?0:1;
    }


    /**
     * 仓库的删除
     * */
    @ResponseBody
    @RequestMapping(value ="/delete/{agentName}/{repoName}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public int deleteRepo(@PathVariable("agentName") String agentName, @PathVariable("repoName") String repoName) {
        return repositoryService.deleteRepository(localPath, agentName, repoName)?1:0;
    }
}








