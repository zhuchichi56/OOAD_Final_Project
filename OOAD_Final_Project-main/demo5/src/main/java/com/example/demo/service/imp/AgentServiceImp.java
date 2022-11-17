package com.example.demo.service.imp;


import com.example.demo.entity.Agent;
import com.example.demo.entity.Repo;
import com.example.demo.mapper.*;
import com.example.demo.service.AgentService;
import com.example.demo.util.encodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
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




    /**
     * 用户注册
     * **/
    //直接返回User的自增主键；
    @Override
    public String createUser(Agent agent) {
        int count = agentMapper.createAgent(agent);
        return agent.getAgentName();
    }





    public List<Repo> getRepoByName(String UserName) {
        return repositoryMapper.getAllRepo(UserName);
    }








    @Override
    public int updateUserName(String old, String latest) {

        List<String> repoIds = repositoryMapper.getAllRepoId(old);
        for (String repoId : repoIds) {
            Repo repo = repositoryMapper.getRepoById(repoId);
//            repositoryMapper.updateOwnerName(old, latest);
            repositoryMapper.updateRepoId(repoId, encodeUtil.hash(latest, repo.getRepoName()));
        }
        return agentMapper.updateUserName(old, latest);
    }






    @Override
    public int updateUserIcon(String localPath, String name, byte[] imageData) {
        String url = localPath + name + ".jpg";
        Image image = Toolkit.getDefaultToolkit().createImage(imageData);
        try {
            ImageIO.write((RenderedImage) image,"JPEG", new File(url));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return agentMapper.updateUserIcon(name,url);
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
    public int starNewRepo(String agentName, String repoId) {
        return starRepoMapper.starRepo(agentName,repoId);
    }

    @Override
    public int removeStarToRepo(String agentName, String repoId) {
        return starRepoMapper.removeStar(agentName,repoId);
    }



    @Override
    public List<Repo> allRepoAgentStar(String agentName) {
        List<String> repoIdList = starRepoMapper.getAllStarRepos(agentName);
        return repoIdList.stream().map(o->repositoryMapper.getRepoById(o)).collect(Collectors.toList());
    }



}
