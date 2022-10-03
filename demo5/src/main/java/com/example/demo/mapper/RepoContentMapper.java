package com.example.demo.mapper;

import com.example.demo.entity.Branch;
import com.example.demo.entity.RepoContent;
import com.example.demo.entity.StaticRepo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RepoContentMapper {

    int createNewRepo(RepoContent content);
    RepoContent selectRepoContentByRepoId(@Param("repoId") int repoId);
}
