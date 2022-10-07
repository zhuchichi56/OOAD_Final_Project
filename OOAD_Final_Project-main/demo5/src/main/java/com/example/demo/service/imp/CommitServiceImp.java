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
    public int initRepository(int AgentId, String RepoName) {
        TransactionStatus transaction = TransactionUtil.getTransaction(dataSourceTransactionManager);
        try {
            StaticRepo staticRepo = new StaticRepo(AgentId,RepoName);
            RepoContent repoContent = new RepoContent();
            staticRepoMapper.createNewStaticRepo(staticRepo);
            repoContentMapper.createNewRepoContent(repoContent);
            int rootCurrentId = repoContent.getRepoId();
            branchMapper.createNewBranch(new Branch(AgentId,RepoName,rootCurrentId,rootCurrentId));
            vcMapper.createNewVc(rootCurrentId,rootCurrentId);
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
    public int commitFiles(int AgentId, String RepoName, String BranchName, String Content, String Comment, String Date) {
        TransactionStatus transaction = TransactionUtil.getTransaction(dataSourceTransactionManager);
        try {
            //新创建一个repoContent 并直接返回 新的主键 childId
            RepoContent repoContent = new RepoContent(Content,Comment,Date);
            repoContentMapper.createNewRepoContent(repoContent);
            int childId = repoContent.getRepoId();


            //保留之前的currentId 作为FatherId
            Branch branch = branchMapper.selectBranchAll(AgentId,RepoName,BranchName);

            // 增加VC表
            vcMapper.createNewVc(branch.getCurrentRepoId(),childId);

            //更新到最新的 childId
            branchMapper.updateBranchCurrentId(AgentId,RepoName,BranchName,childId);
            dataSourceTransactionManager.commit(transaction);
        }catch (Exception e){
            e.printStackTrace();
            dataSourceTransactionManager.rollback(transaction);
            return 0;
        }
        return 1;
    }

}



