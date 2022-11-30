package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.entity.PullRequest;
import com.example.demo.entity.Repo;
import com.example.demo.mapper.ForkRepoMapper;
import com.example.demo.mapper.RepositoryMapper;
import com.example.demo.service.*;
import com.example.demo.util.BranchUtil;
import com.example.demo.util.DateParser;
import com.example.demo.util.encodeUtil;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping(value = "/PR")
public class PRController {
    String localPath = "/Users/zhuhe/Desktop/Jgit";
    String Empty = "/Users/zhuhe/Desktop/Empty/";
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    BranchService branchService;
    @Autowired
    CommitService commitService;

    @Autowired
    AgentService agentService;

    @Autowired
    PullService pullService;

    @Autowired
    RepositoryMapper repositoryMapper;

    @Autowired
    ForkRepoMapper forkRepoMapper;



    /*
     * Finish!
     * */
    @ResponseBody
    @RequestMapping(value ="/fork/{forkName}/{forkRepoName}/{targetAgentName}/{targetRepoName}/{targetBranch}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public int fork(        @PathVariable("targetAgentName") String targetAgentName,
                            @PathVariable("targetRepoName") String targetRepoName,
                            @PathVariable("targetBranch") String targetBranch,
                            @PathVariable("forkName") String forkName,
                            @PathVariable("forkRepoName") String forkRepoName ) throws GitAPIException {

        Git target  = repositoryService.loadLocalRepository(localPath,targetAgentName,targetRepoName);
//        System.out.println(BranchUtil.getAllBranch(target));
        branchService.switchBranch(target,"master");
        Git forgit  = repositoryService.forkRepository(localPath,targetAgentName,targetRepoName,targetBranch,forkName,forkRepoName);

//        if(forgit!=null) {
//            forkRepoMapper.forkRepo(forkName, repositoryMapper.getRepoId(targetAgentName, targetRepoName));
//        }
        return forgit==null?0:1;
    }



    /*
    * Finish!
    * */
    @ResponseBody
    @RequestMapping(value ="/getTargetInfo/{forkName}/{forkRepoName}/{forkBranch}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public JSONObject getTarget(
                            @PathVariable("forkName") String forkName,
                            @PathVariable("forkRepoName") String forkRepoName   ,
                            @PathVariable("forkBranch") String forkBranch) throws GitAPIException {

        Repo forkrepo = repositoryMapper.getRepoById(repositoryMapper.getRepoId(forkName,forkRepoName));



        String ownerRepoId = forkrepo.getOwnerRepoId();
        System.out.println(ownerRepoId);




        Repo ownerrepo  = repositoryMapper.getRepoById(ownerRepoId);
        Git ownerrepogit = repositoryService.loadLocalRepository(localPath,ownerrepo.getAgentName(),ownerrepo.getRepoName());
        JSONObject result = new JSONObject();
        result.put("targetAgentName",ownerrepo.getAgentName());
        result.put("targetRepoName",ownerrepo.getRepoName());
        List<String> branchNameList = BranchUtil.getAllBranch(ownerrepogit);

        List<JSONObject> branchNameJson = new ArrayList<>();
        for (String branchName:branchNameList) {
            JSONObject sub = new JSONObject();
            sub.put("branchName",branchName);
            branchNameJson.add(sub);
        }



        result.put("targetBranch",branchNameJson);
        return result;
    }


    @ResponseBody
    @RequestMapping(value ="/merge/{targetAgentName}/{targetRepoName}/{targetBranch}/{forkName}/{forkRepoName}/{forkBranch}/{prId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String Merge ( @PathVariable("targetAgentName") String targetAgentName,
                          @PathVariable("targetRepoName") String targetRepoName,
                          @PathVariable("targetBranch") String targetBranch, //base
                          @PathVariable("forkName") String forkName,
                          @PathVariable("forkRepoName") String forkRepoName,
                          @PathVariable("forkBranch") String forkBranch ,//fork
                          @PathVariable("prId") String prId //fork
    ) throws GitAPIException, IOException {
        JSONObject result = new JSONObject();


        //这个是目标的仓库
        Git target = repositoryService.loadLocalRepository(localPath, targetAgentName, targetRepoName);
        //这个是fork的人的仓库
        Git fork = repositoryService.loadLocalRepository(localPath, forkName, forkRepoName);

        List<String> responce = null;
        //是同一人;
        if(Objects.equals(targetAgentName, forkName)){
            responce  = branchService.merge(fork,targetBranch,forkBranch);
        }  else {

            branchService.switchBranch(fork,"master");
            branchService.push(fork,"master","temp_branch$");
            try {
                target.commit().setMessage("Commit!").call();
            }
            catch (Exception e){}
            responce  = branchService.merge(target,targetBranch,"temp_branch$");
            branchService.deleteBranchForce(target,"temp_branch$");
        }
        result.put("status",responce.get(0));
        List<String> list_ = new ArrayList<>();
        for (int i = 1; i <responce.size() ; i++) {
            list_.add(responce.get(i));
        }
        result.put("status",responce.get(0));
        result.put("List",list_);

        PullRequest pullRequest = new PullRequest();
        pullRequest.setPrId(prId);
        pullRequest.setTitle("random");
        pullRequest.setRepositoryId(repositoryMapper.getRepoId(forkName, forkRepoName));
        pullRequest.setTargetId(repositoryMapper.getRepoId(targetAgentName, targetRepoName));
        pullRequest.setTargetBranch(targetBranch);
        pullRequest.setBranch(forkBranch);
        pullRequest.setAgentName(forkName);
        pullRequest.setIsClosed(1);

        if(responce.get(0).equals( "Conflicting")) {
            pullRequest.setStatus(1);
        }else {
            pullRequest.setStatus(2);
        }

        System.out.println(pullRequest.toString());

        pullService.updatePull(pullRequest);
        return result.toString();
    }




    /*完成request 方法*/
    /*
    * 接受到请求加入数据库中
    * 返回一个json字符串
    *
    * */
    @RequestMapping(value ="/Request/{targetAgentName}/{targetRepoName}/{targetBranch}/{forkName}/{forkRepoName}/{forkBranch}/{title}",
            method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public int Request(
                            @PathVariable("targetAgentName") String targetAgentName,
                             @PathVariable("targetRepoName") String targetRepoName,
                             @PathVariable("targetBranch") String targetBranch,
                             @PathVariable("forkName") String forkName,
                             @PathVariable("forkRepoName") String forkRepoName,
                             @PathVariable("forkBranch") String forkBranch,
                             @PathVariable("title") String title
    ) {


        PullRequest pullRequest = new PullRequest();
        pullRequest.setPrId(encodeUtil.hash(DateParser.getCurrentDate()));
        pullRequest.setTitle(title);

        pullRequest.setRepositoryId(repositoryMapper.getRepoId(forkName,forkRepoName));
        pullRequest.setTargetId(repositoryMapper.getRepoId(targetAgentName,targetRepoName));

        pullRequest.setTargetBranch(targetBranch);
        pullRequest.setBranch(forkBranch);
        pullRequest.setAgentName(forkName);
        int a =pullService.createNewPull(pullRequest);
        System.out.println(a==1?"Request Success!":"Request Failed");
        return a;
    }


    @ResponseBody
    @RequestMapping(value ="/rejectMerge/{targetAgentName}/{targetRepoName}/{targetBranch}/{forkName}/{forkRepoName}/{forkBranch}/{prId}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public int rejectById(       @PathVariable("targetAgentName") String targetAgentName,
                                 @PathVariable("targetRepoName") String targetRepoName,
                                 @PathVariable("targetBranch") String targetBranch, //base
                                 @PathVariable("forkName") String forkName,
                                 @PathVariable("forkRepoName") String forkRepoName,
                                 @PathVariable("forkBranch") String forkBranch,
                                 @PathVariable("prId") String prId
    ) {
        PullRequest pullRequest = new PullRequest();
        pullRequest.setPrId(prId);
        pullRequest.setTitle("random");
        pullRequest.setRepositoryId(repositoryMapper.getRepoId(forkName, forkRepoName));
        pullRequest.setTargetId(repositoryMapper.getRepoId(targetAgentName, targetRepoName));
        pullRequest.setTargetBranch(targetBranch);
        pullRequest.setBranch(forkBranch);
        pullRequest.setAgentName(forkName);
        pullRequest.setIsClosed(1);
        System.out.println(pullRequest.getIsClosed());
        return pullService.updatePull(pullRequest);
    }


}







