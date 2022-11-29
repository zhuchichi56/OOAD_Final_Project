package com.example.demo.service.imp;


import com.example.demo.entity.Agent;
import com.example.demo.entity.Repo;
import com.example.demo.mapper.*;
import com.example.demo.service.AgentService;
import com.example.demo.util.FileCoverUtil;
import com.example.demo.util.encodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgentServiceImp implements AgentService {

    @Autowired
    AgentMapper agentMapper;
    @Autowired
    StarRepoMapper starRepoMapper;
    @Autowired
    ContributorMapper contributorMapper;
    @Autowired
    RepositoryMapper repositoryMapper;

    @Autowired
    IssueMapper issueMapper;

    @Autowired
    CommentMapper commentMapper;



    @Override
    public int createUser(Agent agent) {
        return agentMapper.createAgent(agent);
//        return agent.getAgentName();
    }



    @Override
    public int deleteUser(String localPath,String name) {
        agentMapper.deleteUser(name);
        File f = new File(localPath + File.separator + name);
        FileCoverUtil.deleteFolder(f);
        return 1;
    }

    @Override
    public int CheckUser(String agentname, String password) {
        System.out.println(agentMapper.checkUser(agentname,password));
        return agentMapper.checkUser(agentname,password);
    }
    

    public List<Repo> getRepoByName(String UserName) {
        return repositoryMapper.getAllRepo(UserName);
    }


    @Override
    public List<Repo> getRepoByContributor(String UserName) {
         return contributorMapper.getAllRepoId(UserName).stream().map(o->repositoryMapper.getRepoById(o)).collect(Collectors.toList());
    }






    @Override
    public int updateUserName(String old, String latest) {
        if (agentMapper.checkUserName(latest) != 0){
            return 0;
        }
        List<String> repoIds = repositoryMapper.getAllRepoId(old);
        for (String repoId : repoIds) {
            Repo repo = repositoryMapper.getRepoById(repoId);
//            repositoryMapper.updateOwnerName(old, latest);
            repositoryMapper.updateRepoId(repoId, encodeUtil.hash(latest, repo.getRepoName()));
        }
        return agentMapper.updateUserName(old, latest);
    }

    @Override
    public int updateUserPassword(String name, String oldPassword, String newPassword) {
        if (agentMapper.checkUser(name, oldPassword) == 1){
            return agentMapper.updateUserPassword(name, newPassword);
        }
        return 0;
    }


    /**
     * 仓库拥有者邀请其它用户作为贡献者加入仓库（未测试）
     * **/
    @Override
    public int inviteContributor(String contributorName, String repositoryId) {
        return contributorMapper.insertNewContributor(contributorName,repositoryId);
    }


    /**
     * 检查某个用户是不是某个仓库的贡献者（未测试）
     * **/
    @Override
    public int checkContributor(String contributorName, String repositoryId) {
        return contributorMapper.checkContributor(contributorName, repositoryId);
    }

    @Override
    public int removeContributor(String contributorName, String repositoryId) {
        return contributorMapper.removeContributor(contributorName, repositoryId);
    }


    @Override
    public int starNewRepo(String agentName, String repoId) {
        try {
            starRepoMapper.starRepo(agentName,repoId);
        }catch (Exception e){
            return 0;
        }
        return 1;
    }

    @Override
    public int removeStarToRepo(String agentName, String repoId) {
        return starRepoMapper.removeStar(agentName,repoId);
    }

    @Override
    public List<String> getContributors(String repositoryId) {
        return contributorMapper.getAllContributors(repositoryId);
    }


    @Override
    public List<Repo> allRepoAgentStar(String agentName) {
        List<String> repoIdList = starRepoMapper.getAllStarRepos(agentName);
        return repoIdList.stream().map(o->repositoryMapper.getRepoById(o)).collect(Collectors.toList());
    }



}
