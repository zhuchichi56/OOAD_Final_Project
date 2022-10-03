package com.example.demo.mapper;

import com.example.demo.entity.Branch;
import com.example.demo.entity.RepoContent;
import com.example.demo.entity.Repository;
import com.example.demo.entity.StaticRepo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RepositoryMapper {

    int createNewRepo(@Param("static") StaticRepo staticRepo, @Param("content") RepoContent content);

    int createNewStaticRepo(StaticRepo staticRepo);

    int createNewBranch(@Param("static") StaticRepo staticRepo, @Param("branch") Branch branch);
}
