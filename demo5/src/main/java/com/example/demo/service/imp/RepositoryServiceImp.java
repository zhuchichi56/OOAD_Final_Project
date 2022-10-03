package com.example.demo.service.imp;

import com.example.demo.entity.Branch;
import com.example.demo.entity.RepoContent;
import com.example.demo.entity.StaticRepo;
import com.example.demo.mapper.BranchMapper;
import com.example.demo.mapper.RepoContentMapper;
import com.example.demo.mapper.StaticRepoMapper;
import com.example.demo.service.RepositoryService;
import com.example.demo.util.TransactionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

@Service
public class RepositoryServiceImp implements RepositoryService {
    @Autowired
    RepoContentMapper repoContentMapper;

    @Autowired
    StaticRepoMapper staticRepoMapper;

    @Autowired
    BranchMapper branchMapper;

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    @Override
    public int createNewRepo(String name, int agentId, String content, String commit_time) {
        Branch branch = new Branch("main",0,0);
        RepoContent repoContent = new RepoContent(0,content, "the main branch", commit_time, "1.0");
        StaticRepo staticRepo = new StaticRepo(name,0,0,agentId);
        TransactionStatus transaction = TransactionUtil.getTransaction(dataSourceTransactionManager);
        try {
            repoContentMapper.createNewRepo(repoContent);
            staticRepoMapper.createNewStaticRepo(staticRepo);
            branchMapper.createNewBranch(staticRepo,branch);
            dataSourceTransactionManager.commit(transaction);
        }catch (Exception e){
            e.printStackTrace();
            dataSourceTransactionManager.rollback(transaction);
            return 0;
        }
        return 1;
    }
}
