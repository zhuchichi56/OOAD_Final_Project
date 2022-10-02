package com.example.demo.service.imp;

import com.example.demo.entity.Repository;
import com.example.demo.mapper.RepositoryMapper;
import com.example.demo.service.RepositoryService;
import com.example.demo.util.TransactionUtil;
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
    public int createNewRepo(String name, int AgentId, String content, String commit_time) {
        Repository repo = new Repository(name, 0, 0, AgentId, 1, "main", content, "the main branch", commit_time);
        TransactionStatus transaction = TransactionUtil.getTransaction(dataSourceTransactionManager);
        try {
            repositoryMapper.createNewRepo(repo);
            repositoryMapper.createNewStaticRepo(repo);
            repositoryMapper.createNewBranch(repo);
            dataSourceTransactionManager.commit(transaction);
        }catch (Exception e){
            e.printStackTrace();
            dataSourceTransactionManager.rollback(transaction);
            return 0;
        }
        return 1;
    }
}
