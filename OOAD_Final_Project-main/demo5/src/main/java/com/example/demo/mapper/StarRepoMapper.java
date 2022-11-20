package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StarRepoMapper {

    int starRepo(@Param("agentName") String name, @Param("repoId") String repoId);

    int removeStar(@Param("agentName") String name, @Param("repoId") String repoId);

    List<String> getAllStarRepos(@Param("agentName") String name);

    int removeStarByRepoId(@Param("repoId")String repoId);
//    int updateStarUserName(@Param("oldName") String oldName, @Param("newName") String newName);

}
