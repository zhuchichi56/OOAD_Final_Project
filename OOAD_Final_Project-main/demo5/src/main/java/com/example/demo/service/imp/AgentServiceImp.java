package com.example.demo.service.imp;


import com.example.demo.entity.Agent;
import com.example.demo.mapper.AgentMapper;
import com.example.demo.mapper.ContributorMapper;
import com.example.demo.service.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgentServiceImp implements AgentService {

    @Autowired
    AgentMapper agentMapper;
    @Autowired
    ContributorMapper contributorMapper;

    /**
     * 用户注册
     * **/
    //直接返回User的自增主键；
    @Override
    public int createName(Agent agent) {

        int count = agentMapper.createAgent(agent);
        return agent.getAgentId();
    }
    /**
     * 仓库拥有者邀请其它用户作为贡献者加入仓库（未测试）
     * **/
    @Override
    public int inviteContributor(Agent agent, Agent contributor, String repoName) {
        return contributorMapper.insertNewContributor(contributor.getAgentId(),agent.getAgentId(),repoName);
    }

    /**
     * 检查某个用户是不是某个仓库的贡献者（未测试）
     * **/
    @Override
    public int checkContributor(Agent agent, Agent contributor, String repoName) {
        return contributorMapper.checkContributor(contributor.getAgentId(),agent.getAgentId(),repoName);
    }

}
