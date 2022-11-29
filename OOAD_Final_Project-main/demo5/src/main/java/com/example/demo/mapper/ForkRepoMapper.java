package com.example.demo.mapper;

import org.apache.ibatis.annotations.Param;


public interface ForkRepoMapper {
    int forkRepo(@Param("agentName") String name, @Param("repoId") String repoId);

    int removeFork(@Param("agentName") String name, @Param("repoId") String repoId);
}
