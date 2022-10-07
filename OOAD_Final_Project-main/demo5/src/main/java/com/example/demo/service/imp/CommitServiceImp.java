package com.example.demo.service.imp;

import com.example.demo.entity.Branch;
import com.example.demo.entity.RepoContent;
import com.example.demo.entity.StaticRepo;
import com.example.demo.mapper.*;
import com.example.demo.service.CommitService;
import com.example.demo.util.TransactionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;



@Service
public class CommitServiceImp implements CommitService {


    @Autowired
    AgentMapper agentMapper;


    @Autowired
    BranchMapper branchMapper;


    @Autowired
    RepoContentMapper repoContentMapper;


    @Autowired
    StaticRepoMapper staticRepoMapper;


    @Autowired
    VCMapper vcMapper;

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;



 /**
  * 创建一个空的仓库，并创建主分支Master
  * **/
    @Override
    public int initRepository(int agentId, String repoName) {
        TransactionStatus transaction = TransactionUtil.getTransaction(dataSourceTransactionManager);
        try {
            StaticRepo staticRepo = new StaticRepo(agentId,repoName);
            RepoContent repoContent = new RepoContent();
            staticRepoMapper.createNewStaticRepo(staticRepo);
            repoContentMapper.createNewRepoContent(repoContent);
            int rootCurrentId = repoContent.getRepoId();
            branchMapper.createNewBranch(new Branch(agentId,repoName,rootCurrentId,rootCurrentId));
            dataSourceTransactionManager.commit(transaction);
        }catch (Exception e){
            e.printStackTrace();
            dataSourceTransactionManager.rollback(transaction);
            return 0;
        }
        return 1;

    }


/**
 * 指定分支提交数据
 * */

    @Override
    public int commitFiles(int agentId, String repoName, String branchName, String content, String comment, String date) {
        TransactionStatus transaction = TransactionUtil.getTransaction(dataSourceTransactionManager);
        try {
            //新创建一个repoContent 并直接返回 新的主键 childId
            RepoContent repoContent = new RepoContent(content,comment,date);
            repoContentMapper.createNewRepoContent(repoContent);
            int childId = repoContent.getRepoId();

            //保留之前的currentId 作为FatherId
            Branch branch = branchMapper.selectBranchAll(agentId,repoName,branchName);

            // 增加VC表
            vcMapper.createNewVc(branch.getCurrentRepoId(),childId);

            //更新到最新的 childId
            branchMapper.updateBranchCurrentId(agentId,repoName,branchName,childId);
            dataSourceTransactionManager.commit(transaction);
        }catch (Exception e){
            e.printStackTrace();
            dataSourceTransactionManager.rollback(transaction);
            return 0;
        }
        return 1;
    }

}



