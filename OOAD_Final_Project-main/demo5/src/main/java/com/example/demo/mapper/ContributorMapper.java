package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ContributorMapper {

    int insertNewContributor(@Param("contributorId") int contributorId,@Param("ownerId") int ownerId,@Param("repoName") String repoName);

    int checkContributor(@Param("contributorId") int contributorId,@Param("ownerId") int ownerId,@Param("repoName") String repoName);
}

