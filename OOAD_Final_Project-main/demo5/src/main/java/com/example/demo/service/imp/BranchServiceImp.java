package com.example.demo.service.imp;


import com.example.demo.service.BranchService;
import com.example.demo.util.BranchUtil;
import org.eclipse.jgit.api.*;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class BranchServiceImp implements BranchService {



    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;





    @Override
    public Ref createBranch(Git repository, String baseName,String branchName) throws GitAPIException {


        Ref branch;
//        Git repository = repositoryService.loadLocalRepository(localPath, agentName, repoName);
        repository.checkout().setName(baseName).call();

        //需要判断是不是自己;
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

        List<String> difference = new ArrayList<>();
        difference.add(String.valueOf(mergeResult.getMergeStatus()));
        if(mergeResult.getMergeStatus().equals(MergeResult.MergeStatus.CONFLICTING)) {

            List<DiffEntry> diffs= BranchUtil.listDiff(repository.getRepository() ,repository, base, target);

            for (DiffEntry diffEntry : diffs) {

                difference.add("old: " + diffEntry.getOldPath() +
                        ", new: " + diffEntry.getNewPath() +
                        ", type: " + diffEntry.getChangeType());
            }
        }

        System.out.println(mergeResult.getMergeStatus());
        return difference;
    }





//    @Override
//    public Git pull(String branchName, Git localRepository, String remotePath) throws GitAPIException {
//        if(BranchUtil.branchExist(localRepository, branchName)) {
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
    public List<String> deleteBranchForce(Git repository, String branch) throws GitAPIException {
        return repository.branchDelete().setForce(true)
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







    /**
     * 仅测试文件是否产生冲突
     * */

    @Override
    public List<String> mergeNoCommit(Git repository, String baseBranch, String targetBranch) throws GitAPIException, IOException {
        Ref checkout = repository.checkout().setName(baseBranch).call();
        ObjectId base = checkout.getObjectId();
        ObjectId target = repository.getRepository().resolve(targetBranch);
        List<String> difference = new ArrayList<>();
        List<DiffEntry> diffs= BranchUtil.listDiff(repository.getRepository() ,repository, base, target);
        for (DiffEntry diffEntry : diffs) {
            difference.add("old: " + diffEntry.getOldPath() +
                    ", new: " + diffEntry.getNewPath() +
                    ", type: " + diffEntry.getChangeType());
        }

        return difference;
    }

//    @Override
//    public int clearBranch(Repo repo, String branch, String localPath) {
//        String repoPath = localPath + File.separator + repo.getAgentName() + File.separator + repo.getRepoName();
//        File emptyFile = new File("C:\\Users\\12078\\Desktop\\empty");
//        return commitFiles(localPath,repo.getAgentName(),repo.getRepoName(),branch,emptyFile,repoPath);
//    }





    @Override
    public boolean push(Git repository, String sourceBranch, String destinationBranch) {

        PushCommand command = repository.push().setPushAll();
        boolean ret = true;
        RefSpec refSpec = new RefSpec().setSourceDestination(sourceBranch, destinationBranch);
        command.setRefSpecs(refSpec);
        try {
            List<Ref> remoteBranches = repository.branchList().setListMode(ListBranchCommand.ListMode.REMOTE).call();

            Iterable<PushResult> results = command.call();
            for (PushResult pushResult : results) {
                Collection<RemoteRefUpdate> resultsCollection = pushResult.getRemoteUpdates();
                Map<PushResult, RemoteRefUpdate> resultsMap = new HashMap<>();
                for(RemoteRefUpdate remoteRefUpdate : resultsCollection)
                {
                    resultsMap.put(pushResult, remoteRefUpdate);
                }

                RemoteRefUpdate remoteUpdate = pushResult.getRemoteUpdate(destinationBranch);
                if (remoteUpdate != null) {
                    org.eclipse.jgit.transport.RemoteRefUpdate.Status status =
                            remoteUpdate.getStatus();
                    ret =
                            status.equals(org.eclipse.jgit.transport.RemoteRefUpdate.Status.OK)
                                    || status.equals(org.eclipse.jgit.transport.RemoteRefUpdate.Status.UP_TO_DATE);
                }

                if(remoteUpdate == null && !remoteBranches.toString().contains(destinationBranch))
                {
                    for(RemoteRefUpdate resultValue : resultsMap.values())
                    {
                        if(resultValue.toString().contains("REJECTED_OTHER_REASON"))
                        {
                            ret = false;
                        }
                    }
                }
            }
        } catch (Throwable e) {
            throw new RuntimeException(String.format(
                    "Failed to push [%s] into [%s]",
                    sourceBranch,
                    destinationBranch), e);
        }

        return ret;
    }

}
