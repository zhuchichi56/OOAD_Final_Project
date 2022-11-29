package com.example.demo.mapper;

import org.apache.ibatis.annotations.Param;

public interface WatchRepoMapper {

    int watchRepo(@Param("agentName") String name, @Param("repoId") String repoId);

    int removeWatch(@Param("agentName") String name, @Param("repoId") String repoId);

}
