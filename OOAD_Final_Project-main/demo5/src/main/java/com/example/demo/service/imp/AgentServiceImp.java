package com.example.demo.service.imp;


import com.example.demo.entity.Agent;
import com.example.demo.mapper.AgentMapper;
import com.example.demo.mapper.ContributorMapper;
import com.example.demo.mapper.RepositoryMapper;
import com.example.demo.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentServiceImp implements AgentService {

    @Autowired
    AgentMapper agentMapper;
    @Autowired
    ContributorMapper contributorMapper;
    @Autowired
    RepositoryMapper repositoryMapper;

    /**
     * 用户注册
     * **/
    //直接返回User的自增主键；
    @Override
    public String createUser(Agent agent) {

        int count = agentMapper.createAgent(agent);
        return agent.getAgentName();
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

}
