package com.example.demo.service.imp;


import com.example.demo.entity.Branch;

import com.example.demo.service.BranchService;
import com.example.demo.util.TransactionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;


@Service
public class BranchServiceimp implements BranchService {



    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;


    /**
     * 创建子分支
     * **/
    @Override
    public int createNewBranchOnOldBranch() {


        return 1;
    }
}
