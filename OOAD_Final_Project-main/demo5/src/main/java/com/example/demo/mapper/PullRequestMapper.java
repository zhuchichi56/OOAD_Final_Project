package com.example.demo.mapper;

import com.example.demo.entity.PullRequest;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PullRequestMapper {

    int insertPullRequest(@Param("pullRequest")PullRequest pullRequest);

    List<PullRequest> getPullRequestByTarget(@Param("targetId")String targetId);

    int deletePull(@Param("pullRequest")PullRequest pullRequest);

    int updatePull(@Param("pullRequest")PullRequest pullRequest);
}
