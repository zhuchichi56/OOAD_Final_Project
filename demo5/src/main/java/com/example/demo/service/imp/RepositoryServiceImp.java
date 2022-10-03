package com.example.demo.service.imp;

import com.example.demo.entity.Branch;
import com.example.demo.entity.RepoContent;
import com.example.demo.entity.Repository;
import com.example.demo.entity.StaticRepo;
import com.example.demo.mapper.RepositoryMapper;
import com.example.demo.service.RepositoryService;
import com.example.demo.util.TransactionUtil;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RepositoryServiceImp implements RepositoryService {
    @Autowired
    RepositoryMapper repositoryMapper;

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    @Override
    public int createNewRepo(String name, int agentId, String content, String commit_time) {
        Branch branch = new Branch("main",0,0);
        RepoContent repoContent = new RepoContent(content, "the main branch", commit_time, "1.0");
        StaticRepo staticRepo = new StaticRepo(0,name,0,0,agentId);
        TransactionStatus transaction = TransactionUtil.getTransaction(dataSourceTransactionManager);
        try {
            repositoryMapper.createNewRepo(staticRepo,repoContent);
            repositoryMapper.createNewStaticRepo(staticRepo);
            repositoryMapper.createNewBranch(staticRepo,branch);
            dataSourceTransactionManager.commit(transaction);
        }catch (Exception e){
            e.printStackTrace();
            dataSourceTransactionManager.rollback(transaction);
            return 0;
        }
        return 1;
    }
}
