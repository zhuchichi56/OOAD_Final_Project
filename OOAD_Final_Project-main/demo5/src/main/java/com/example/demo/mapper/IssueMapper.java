package com.example.demo.mapper;

import com.example.demo.entity.Issue;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IssueMapper {
    int createIssue(@Param("issueId") String issueId, @Param("agentName") String agent,
                    @Param("repoId") String repoId, @Param("title") String title, @Param("time") String time,
                    @Param("isClose") String isClose

    );

    List<Issue> getIssues(@Param("repoId") String repoId);

    int deleteIssue(@Param("issueId") String issueId);

    int updateOpen(@Param("issueId") String issueId, @Param("isClose") int isClose);


//    int updateCreatorName(@Param("oldName") String oldName, @Param("newName") String newName);

}
