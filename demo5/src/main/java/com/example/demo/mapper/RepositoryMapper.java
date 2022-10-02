package com.example.demo.mapper;

import com.example.demo.entity.Repository;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RepositoryMapper {

    int createNewRepo(Repository repository);

    int createNewStaticRepo(Repository repository);

    int createNewBranch(Repository repository);
}
