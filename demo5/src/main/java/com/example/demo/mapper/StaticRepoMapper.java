package com.example.demo.mapper;

import com.example.demo.entity.StaticRepo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StaticRepoMapper {

    int createNewStaticRepo(StaticRepo staticRepo);
    StaticRepo getStaticRepoByRepoName (@Param("repoName") String repoName, @Param("agentId") int agentId);
}
