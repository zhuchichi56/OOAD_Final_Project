package com.example.demo.mapper;

import com.example.demo.entity.repository;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface repositoryMapper {

    int createNewRepo(repository repository);

}
