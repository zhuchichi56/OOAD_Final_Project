package com.example.demo.mapper;

import com.example.demo.entity.RepoContent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface RepoContentMapper {

    int createNewRepoContent(@Param("repoContent") RepoContent repoContent);

    RepoContent selectRepoContent(@Param("repoId") int RepoId);





}
