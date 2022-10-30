package com.example.demo.service.imp;


import com.example.demo.service.BranchService;
import com.example.demo.util.BranchUtil;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;


@Service
public class BranchServiceImp implements BranchService {



    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;
    private String localPath = "C:\\Users\\12078\\Desktop\\大三上\\ooad\\test";


    @Override
    public Ref createBranch(Git repository, String branchName) throws GitAPIException {
        Ref branch;

        if(!BranchUtil.branchExist(repository, branchName)) {
            branch = repository.branchCreate().setName(branchName).setForce(false).call();
        }else{
            branch = repository.checkout().setName(branchName).call();
        }

        return branch;
    }

    @Override
    public Ref switchBranch(Git repository, String branchName) throws GitAPIException {
//        if(BranchUtil.branchExist(repository, branchName)) {
//            return repository.checkout().setName(branchName).call();
//        }
//        return null;

        return repository.checkout().setName(branchName).call();
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
            createBranch(localRepository,  branchName);
        }
        localRepository.pull().call();
        return localRepository;
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
