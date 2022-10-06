package com.example.demo.service.imp;


import com.example.demo.entity.Branch;
import com.example.demo.mapper.BranchMapper;
import com.example.demo.service.BranchService;
import com.example.demo.util.TransactionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;


@Service
public class BranchServiceimp implements BranchService {


    @Autowired
    BranchMapper branchMapper;
    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;


    /**
     * 创建子分支
     * **/
    @Override
    public int createNewBranchOnOldBranch(int AgentId, String RepoName, String BranchName, String BaseBranch) {

        TransactionStatus transaction = TransactionUtil.getTransaction(dataSourceTransactionManager);
        try {

            Branch basebranch = branchMapper.selectBranchAll(AgentId, RepoName, BaseBranch);
            // 创建一个新的分支
            return branchMapper.createNewBranch(new Branch(AgentId, RepoName, BranchName, basebranch.getRootRepoId(), basebranch.getCurrentRepoId()));
        }
        catch (Exception e){
            e.printStackTrace();
            dataSourceTransactionManager.rollback(transaction);
            return 0;
        }

    }
}
