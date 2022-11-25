package com.example.demo.service.imp;


import com.example.demo.service.BranchService;
import com.example.demo.util.BranchUtil;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;


@Service
public class BranchServiceImp implements BranchService {



    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;




    @Override
    public Ref createBranch(Git repository, RevCommit startPoint, String branchName) throws GitAPIException {
        Ref branch;

        if(!BranchUtil.branchExist(repository, branchName)) {
            branch = repository.branchCreate().setName(branchName).setStartPoint(startPoint).setForce(false).call();
        }else{
            branch = repository.checkout().setName(branchName).call();
        }

        return branch;
    }

    @Override
    public Ref switchBranch(Git repository, String branchName) throws GitAPIException {
        if(BranchUtil.branchExist(repository, branchName)) {
            return repository.checkout().setName(branchName).call();
        }
        return null;
    }

    @Override
    public void merge(Git repository, String baseBranch, String targetBranch) throws GitAPIException, IOException {
        Ref checkout = repository.checkout().setName(baseBranch).call();
        ObjectId base = checkout.getObjectId();
        ObjectId target = repository.getRepository().resolve(targetBranch);

        MergeResult mergeResult = repository.merge().include(target).setCommit(true).setMessage("Merge request").call();
        if(mergeResult.getMergeStatus().equals(MergeResult.MergeStatus.CONFLICTING)) {

            BranchUtil.listDiff(repository.getRepository() ,repository, base, target);
        }

    }

    @Override
    public Git Pull(String branchName, Git localRepository, String remotePath) throws GitAPIException {
        if(BranchUtil.branchExist(localRepository, branchName)) {
            switchBranch(localRepository, branchName);
        }else {
            createBranch(localRepository, null, branchName);
        }
        localRepository.pull().call();
        return localRepository;
    }
}
