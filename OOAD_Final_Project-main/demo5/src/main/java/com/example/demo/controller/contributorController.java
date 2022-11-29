package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.mapper.ContributorMapper;
import com.example.demo.mapper.RepositoryMapper;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/Contributor")
public class ContributorController {
    @Autowired
    ContributorMapper contributorMapper;

    @Autowired
    RepositoryMapper repositoryMapper;

    @ResponseBody
    @RequestMapping(value ="/addContributor/{contributorName}/{ownerName}/{repoName}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String addContributor(@PathVariable("contributorName") String contributorName,
                              @PathVariable("ownerName") String ownerName,
                              @PathVariable("repoName") String repoName

    ) {
        JSONObject result = new JSONObject();

        String repoId = repositoryMapper.getRepoId(ownerName, repoName);
        int status = contributorMapper.insertNewContributor(contributorName, repoId);
        if(status == 1) result.put("status", 1);
        else result.put("status", 0);
        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value ="/removeContributor/{contributorName}/{ownerName}/{repoName}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String removeContributor(@PathVariable("contributorName") String contributorName,
                              @PathVariable("ownerName") String ownerName,
                              @PathVariable("repoName") String repoName

    ) {
        JSONObject result = new JSONObject();

        String repoId = repositoryMapper.getRepoId(ownerName, repoName);
        int status = contributorMapper.removeContributor(contributorName, repoId);
        if(status == 1) result.put("status", 1);
        else result.put("status", 0);
        return result.toJSONString();
    }

    @ResponseBody
    @RequestMapping(value ="/checkContributor/{contributorName}/{ownerName}/{repoName}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String checkContributor(@PathVariable("contributorName") String contributorName,
                                    @PathVariable("ownerName") String ownerName,
                                    @PathVariable("repoName") String repoName

    ) {
        JSONObject result = new JSONObject();

        String repoId = repositoryMapper.getRepoId(ownerName, repoName);
        int status = contributorMapper.checkContributor(contributorName, repoId);
        if(status == 1) result.put("isContributor", 1);
        else result.put("isContributor", 0);
        return result.toJSONString();
    }


    @ResponseBody
    @RequestMapping(value ="/listContributors/{ownerName}/{repoName}", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String checkContributor(@PathVariable("ownerName") String ownerName,
                                   @PathVariable("repoName") String repoName

    ) {


        String repoId = repositoryMapper.getRepoId(ownerName, repoName);
        List<String> contributors = contributorMapper.getAllContributors(repoId);
        Gson gson = new Gson();
        Map<String, List<String>> result = new HashMap<>();
        result.put("contributors", contributors);

        return gson.toJson(result);
    }


}
