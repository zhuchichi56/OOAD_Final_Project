package com.example.demo.controller;


import com.example.demo.mapper.ForkRepoMapper;
import com.example.demo.mapper.RepositoryMapper;
import com.example.demo.mapper.StarRepoMapper;
import com.example.demo.mapper.WatchRepoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class star_fork_watch_Controller {

    @Autowired
    StarRepoMapper starRepoMapper;

    @Autowired
    ForkRepoMapper forkRepoMapper;

    @Autowired
    WatchRepoMapper watchRepoMapper;

    @Autowired
    RepositoryMapper repositoryMapper;


    @ResponseBody
    @RequestMapping(value ="/star/add/{repoOwner}/{repoName}/{agentName}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public int AddStar(
            @PathVariable("repoOwner") String repoOwner,
            @PathVariable("repoName") String repoName,
            @PathVariable("agentName") String agentName
    ){
        try {
            starRepoMapper.starRepo(agentName,repositoryMapper.getRepoId(repoOwner,repoName));
        }catch (Exception e){
            return 0;
        }
        return 1;
    }


//    @ResponseBody
//    @RequestMapping(value ="/fork/add/{repoOwner}/{repoName}/{agentName}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//    public int AddFork(
//            @PathVariable("repoOwner") String repoOwner,
//            @PathVariable("repoName") String repoName,
//            @PathVariable("agentName") String agentName
//    ){
//        try {
//            forkRepoMapper.forkRepo(agentName,repositoryMapper.getRepoId(repoOwner,repoName));
//        }catch (Exception e){
//            return 0;
//        }
//        return 1;
//    }




    @ResponseBody
    @RequestMapping(value ="/watch/add/{repoOwner}/{repoName}/{agentName}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public int AddWatch(
            @PathVariable("repoOwner") String repoOwner,
            @PathVariable("repoName") String repoName,
            @PathVariable("agentName") String agentName
    ){
        try {
            watchRepoMapper.watchRepo(agentName,repositoryMapper.getRepoId(repoOwner,repoName));
        }catch (Exception e){
            return 0;
        }
        return 1;
    }


    @ResponseBody
    @RequestMapping(value ="/star/delete/{repoOwner}/{repoName}/{agentName}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public int deleteStar(
            @PathVariable("repoOwner") String repoOwner,
            @PathVariable("repoName") String repoName,
            @PathVariable("agentName") String agentName
    ){
        try {
            starRepoMapper.removeStar(agentName,repositoryMapper.getRepoId(repoOwner,repoName));
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

//    @ResponseBody
//    @RequestMapping(value ="/fork/delete/{repoOwner}/{repoName}/{agentName}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
//    public int deleteFork(
//            @PathVariable("repoOwner") String repoOwner,
//            @PathVariable("repoName") String repoName,
//            @PathVariable("agentName") String agentName
//    ){
//        try {
//            forkRepoMapper.removeFork(agentName,repositoryMapper.getRepoId(repoOwner,repoName));
//        }catch (Exception e){
//            return 0;
//        }
//        return 1;
//    }

    @ResponseBody
    @RequestMapping(value ="/watch/delete/{repoOwner}/{repoName}/{agentName}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public int deleteWatch(
            @PathVariable("repoOwner") String repoOwner,
            @PathVariable("repoName") String repoName,
            @PathVariable("agentName") String agentName
    ){
        try {
            watchRepoMapper.removeWatch(agentName,repositoryMapper.getRepoId(repoOwner,repoName));
        }catch (Exception e){
            return 0;
        }
        return 1;
    }
}






