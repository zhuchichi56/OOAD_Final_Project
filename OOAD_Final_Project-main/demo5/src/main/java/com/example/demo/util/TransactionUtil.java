package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

public class TransactionUtil {
    /*
    * 首先要在你所用的类中注入一个DataSourceTransactionManager
    * */

    public static TransactionStatus getTransaction(DataSourceTransactionManager dataSourceTransactionManager){
        // 获取事务定义
        DefaultTransactionDefinition df = new DefaultTransactionDefinition();
        // 设置事务隔离级别，开启新的数据
        df.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        // 获取事务状态,相当于开启事务
        return dataSourceTransactionManager.getTransaction(df);
    }
}
