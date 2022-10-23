package com.example.demo.service.imp;


import com.example.demo.entity.Branch;

import com.example.demo.service.BranchService;
import com.example.demo.util.TransactionUtil;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.errors.CheckoutConflictException;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import java.io.File;
import java.io.IOException;


@Service
public class BranchServiceimp implements BranchService {



    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;
    private String localPath = "C:\\Users\\12078\\Desktop\\大三上\\ooad\\test";


    /**
     * 创建子分支
     * **/
    @Override
    public int createNewBranchOnOldBranch() {


        return 1;
    }

    /**
     * 给出ObjectId,并回滚至该版本
     * @param agentId
     * @param repoName
     * @param id
     * @return
     */
    @Override
    public int rollback(int agentId,String repoName,String id) {
        String path = localPath+"\\"+agentId+"\\"+repoName;
        try {
            Git git = Git.open(new File(path));
            Repository repository = git.getRepository();
            RevWalk revWalk = new RevWalk(repository);
            ObjectId objectId = repository.resolve(id);
            RevCommit revCommit = revWalk.parseCommit(objectId);
            String preVision = revCommit.getName();
            git.reset().setMode(ResetCommand.ResetType.HARD).setRef(preVision).call();
            repository.close();
        } catch (IOException | GitAPIException e) {
            return -1;
        }
        return 1;
    }
}
