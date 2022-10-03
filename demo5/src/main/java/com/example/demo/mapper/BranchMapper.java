package com.example.demo.mapper;

import com.example.demo.entity.Branch;
import com.example.demo.entity.StaticRepo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface BranchMapper {

    int createNewBranch(@Param("static") StaticRepo staticRepo, @Param("branch") Branch branch);
}
