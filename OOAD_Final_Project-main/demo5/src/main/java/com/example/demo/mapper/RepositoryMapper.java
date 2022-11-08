package com.example.demo.mapper;


import com.example.demo.entity.Repo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RepositoryMapper {

    int createNewRepository(@Param("hash") String hashValue ,@Param("Repository") Repo repo);

    String getRepoId(@Param("agentName") String agentName, @Param("repoName") String repoName);

    Repo getRepoById(@Param("repoId") String repoId);
}
