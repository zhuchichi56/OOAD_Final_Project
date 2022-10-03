package com.example.demo.mapper;

import com.example.demo.entity.StaticRepo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StaticRepoMapper {

    int createNewStaticRepo(StaticRepo staticRepo);
}
