package com.example.demo.mapper;


import com.example.demo.entity.Repo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RepositoryMapper {

    int createNewRepository(@Param("hash") String hashValue ,@Param("Repository") Repo repo);

    String getRepoId(@Param("agentName") String agentName, @Param("repoName") String repoName);

    Repo getRepoById(@Param("repoId") String repoId);

    int updateRepoId(@Param("oldId") String oldId, @Param("newId") String newId);

//    int updateOwnerName(@Param("repoId") String hash,@Param("oldName") String oldName, @Param("newName") String newName);
    int setAuthority(@Param("authority") int authority, @Param("repoId") String repoId);

    List<String> getAllRepoId(@Param("ownerName") String ownerName);


    List<Repo> getAllRepo(@Param("ownerName") String ownerName);


    int deleteRepository(@Param("repoId") String repoId);

    boolean starIsValid(@Param("agentName")String name, @Param("repoId") String repoId);

    boolean forkIsValid(@Param("agentName")String name, @Param("repoId") String repoId);

    boolean watchIsValid(@Param("agentName")String name, @Param("repoId") String repoId);

    int getStar(@Param("repoId") String repoId);

    int getFork(@Param("repoId") String repoId);

    int getWatch(@Param("repoId") String repoId);

}



