package com.example.demo.service.imp;


import com.example.demo.service.BranchService;
import com.example.demo.util.BranchUtil;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.MergeResult;
import org.eclipse.jgit.api.ResetCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class BranchServiceImp implements BranchService {

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;




    @Override
    public Ref createBranch(Git repository, String baseName,String branchName) throws GitAPIException {
        Ref branch;
        repository.checkout().setName(baseName).call();
        if(!BranchUtil.branchExist(repository, branchName)) {
            branch = repository.branchCreate().setName(branchName).setForce(false).call();
        }else{
            branch = repository.checkout().setName(branchName).call();
        }

        return branch;
    }


    @Override
    public Ref switchBranch(Git repository, String branchName) throws GitAPIException {

        return repository.checkout().setName(branchName).call();
    }



    @Override
    public List<String> getContent(String path, String dirPath, String branch) {
        try {
            Git repository = Git.open(new File(path));
            Ref ref = switchBranch(repository,branch);
            File f = new File(dirPath);
            return Arrays.stream(f.listFiles()).map(File::getName).filter(o->!o.equals(".git")).collect(Collectors.toList());
        } catch (GitAPIException | IOException e) {
            throw new RuntimeException(e);
        }
    }



    @Override
    public List<String> merge(Git repository, String baseBranch, String targetBranch) throws GitAPIException, IOException {
        Ref checkout = repository.checkout().setName(baseBranch).call();
        ObjectId base = checkout.getObjectId();
        ObjectId target = repository.getRepository().resolve(targetBranch);

        MergeResult mergeResult = repository.merge().include(target).setCommit(true).setMessage("Merge request").call();
        System.out.println(mergeResult.getMergeStatus());
        List<String> difference = new ArrayList<>();
        if(mergeResult.getMergeStatus().equals(MergeResult.MergeStatus.CONFLICTING)) {

            List<DiffEntry> diffs= BranchUtil.listDiff(repository.getRepository() ,repository, base, target);

            for (DiffEntry diffEntry : diffs) {

                difference.add("old: " + diffEntry.getOldPath() +
                        ", new: " + diffEntry.getNewPath() +
                        ", type: " + diffEntry.getChangeType());
            }
        }
        return difference;
    }



    
//    @Override
//    public Git pull(String branchName, Git localRepository, String remotePath) throws GitAPIException {
//        if(BranchUtil.branchExist(localRepository, branchName)) {
//
//            switchBranch(localRepository, branchName);
//        }else {
//            createBranch(localRepository, branchName);
//        }
//        localRepository.pull().call();
//        return localRepository;
//    }

    @Override
    public List<String> deleteBranch(Git repository, String branch) throws GitAPIException {
        return repository.branchDelete()
                .setBranchNames(branch)
                .call();
    }


    @Override
    public int rollback(Git repository, String branch,String id) {
        try {

            Repository repo = repository.getRepository();
            switchBranch(repository,branch);
            RevWalk revWalk = new RevWalk(repo);
            ObjectId objectId = repo.resolve(id);
            RevCommit revCommit = revWalk.parseCommit(objectId);
            String preVision = revCommit.getName();
            repository.reset().setMode(ResetCommand.ResetType.HARD).setRef(preVision).call();
            repository.close();
        } catch (IOException | GitAPIException e) {
            return 0;
        }
        return 1;
    }





}
