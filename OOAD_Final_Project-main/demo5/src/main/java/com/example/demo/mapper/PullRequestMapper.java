package com.example.demo.mapper;

import com.example.demo.entity.PullRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PullRequestMapper {

    int insertPullRequest(@Param("pullRequest")PullRequest pullRequest);

    List<PullRequest> getPullRequestByTarget(@Param("targetId")String targetId);

    int rejectPull(@Param("repoId") String repoId, @Param("agentName") String agentName, @Param("branch") String branch);
}
