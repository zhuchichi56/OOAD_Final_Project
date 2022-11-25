package com.example.demo.mapper;


import com.example.demo.entity.StaticRepo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface StaticRepoMapper {

    int createNewStaticRepo(@Param("staticRepo")StaticRepo staticRepo);

    int checkRepoInfo(@Param("agentId") int agentId, @Param("repoName") String repoName);
}
