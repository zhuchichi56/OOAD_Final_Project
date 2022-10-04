package com.example.demo.mapper;


import com.example.demo.entity.Branch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface BranchMapper {

    int createNewBranch(@Param("branch") Branch branch);





    //直接搜到
    int createNewBranchOnOldBranch(String OldBranch);


    //得到fatherRepoId;
    Branch selectBranchAll (@Param("agentId")int AgentId,
                               @Param("repoName")String RepoName,
                               @Param("branchName")String BranchName);




    //更新CurrentId 用从RepoID得来的ChildId；
    int updateBranchCurrentId(@Param("agentId") int AgentId,
                              @Param("repoName") String RepoName,
                              @Param("branchName")String BranchName,
                              @Param("currentRepoId") int CurrentRepoId);


}


