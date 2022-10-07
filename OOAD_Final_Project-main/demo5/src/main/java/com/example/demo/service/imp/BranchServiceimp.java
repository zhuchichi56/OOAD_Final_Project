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
    public int createNewBranchOnOldBranch(int agentId, String repoName, String branchName, String baseBranch) {

        TransactionStatus transaction = TransactionUtil.getTransaction(dataSourceTransactionManager);
        try {

            Branch basebranch = branchMapper.selectBranchAll(agentId, repoName, baseBranch);
            // 创建一个新的分支
            int num = branchMapper.createNewBranch(new Branch(agentId, repoName, branchName, basebranch.getRootRepoId(), basebranch.getCurrentRepoId()));
            dataSourceTransactionManager.commit(transaction);
            return num;
        }
        catch (Exception e){
            e.printStackTrace();
            dataSourceTransactionManager.rollback(transaction);
            return 0;
        }

    }
}
