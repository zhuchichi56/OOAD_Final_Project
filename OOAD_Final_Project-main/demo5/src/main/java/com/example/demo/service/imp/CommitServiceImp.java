package com.example.demo.service.imp;


import com.example.demo.mapper.*;
import com.example.demo.service.CommitService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;




@Service
public class CommitServiceImp implements CommitService {


    @Autowired
    AgentMapper agentMapper;





    @Autowired
    StaticRepoMapper staticRepoMapper;




    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;



 /**
  * 创建一个空的仓库，并创建主分支Master
  * **/
    @Override
    public int initRepository() {

        return 1;
    }


/**
 * 指定分支提交数据
 * */

    @Override
    public int commitFiles() {
        return 1;
    }
}



