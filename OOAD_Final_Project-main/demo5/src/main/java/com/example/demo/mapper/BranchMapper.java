package com.example.demo.mapper;


import com.example.demo.entity.Branch;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;


@Mapper
public interface BranchMapper {

    int createNewBranch(@Param("branch") Branch branch);





    //直接搜到
    int createNewBranchOnOldBranch(String oldBranch);


    //得到fatherRepoId;
    Branch selectBranchAll (@Param("agentId")int agentId,
                               @Param("repoName")String repoName,
                               @Param("branchName")String branchName);




    //更新CurrentId 用从RepoID得来的ChildId；
    int updateBranchCurrentId(@Param("agentId") int agentId,
                              @Param("repoName") String repoName,
                              @Param("branchName")String branchName,
                              @Param("currentRepoId") int currentRepoId);


}


