package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ContributorMapper {

    int insertNewContributor(@Param("contributorName") String contributorName,@Param("repositoryId") String hash);

    int checkContributor(@Param("contributorName") String contributorName,@Param("repositoryId") String hash);

    int updateContributorName(@Param("oldName") String oldName, @Param("newName") String newName);
}

