package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ContributorMapper {

    int insertNewContributor(@Param("contributorName") String contributorName,@Param("repositoryId") String hash);

    int checkContributor(@Param("contributorName") String contributorName,@Param("repositoryId") String hash);

    int removeContributor(@Param("contributorName") String contributorName,@Param("repositoryId") String hash);

    List<String> getAllContributors(@Param("repositoryId") String hash);

    List<String> getAllRepoId(@Param("contributorName") String contributorName);


    int deleteContributorByRepoId(@Param("repositoryId") String repositoryId);

//    int updateContributorName(@Param("oldName") String oldName, @Param("newName") String newName);
}

