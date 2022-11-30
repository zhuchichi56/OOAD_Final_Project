package com.example.demo.service.imp;


import com.example.demo.mapper.*;
import com.example.demo.service.CommitService;

import com.example.demo.util.DateParser;
import com.example.demo.util.FileCoverUtil;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.RmCommand;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class CommitServiceImp implements CommitService {
    @Autowired
    AgentMapper agentMapper;

    @Autowired
    RepositoryMapper repositoryMapper;

    public static String flash = "/";



    /**
     * 指定分支提交数据,多用户提交情况还未考虑
     * @param agentName
     * @param repoName
     * @param branch
     * @param file
     * @return
     */

    @Override
    public int commitFiles(String localPath, String agentName, String repoName, String branch, File file) {
        String path = localPath+File.separator+agentName+File.separator+repoName;
        String filePath =localPath+File.separator+agentName+File.separator+repoName+File.separator+file.getName();
        try {
            FileCoverUtil.updateFile(filePath,file);
            File origin = new File(path);
            Git git = Git.open(origin);
            git.checkout().setCreateBranch(false).setName(branch).call();
            git.add().addFilepattern(".").call();
            Status status = git.status().call();
            if (!checkStatus(status)){
                return -1;
            }
            RmCommand rm = git.rm();
            for (String m: status.getMissing()){
                rm.addFilepattern(m).call();
                rm = git.rm();
                status = git.status().call();
            }
            for (String r: status.getRemoved()){
                rm.addFilepattern(r).call();
                rm = git.rm();
                status = git.status().call();
            }
            git.commit().setMessage("default value").call();
        } catch (IOException | GitAPIException e) {
            throw new RuntimeException(e);
        }
        return 1;
    }



    /*
    * 提交一个Empty 即可
    * */
    @Override
    public int commitinnerFiles(String localPath, String agentName, String repoName, String branch, File file) {
        String path = localPath+File.separator+agentName+File.separator+repoName;
        String filePath =localPath+File.separator+agentName+File.separator+repoName;
        try {
            FileCoverUtil.updateFile(filePath,file);
            File origin = new File(path);
            Git git = Git.open(origin);
            git.checkout().setCreateBranch(false).setName(branch).call();
            git.add().addFilepattern(".").call();
            Status status = git.status().call();
            if (!checkStatus(status)){
                return -1;
            }
            RmCommand rm = git.rm();
            for (String m: status.getMissing()){
                rm.addFilepattern(m).call();
                rm = git.rm();
                status = git.status().call();
            }
            for (String r: status.getRemoved()){
                rm.addFilepattern(r).call();
                rm = git.rm();
                status = git.status().call();
            }
            git.commit().setMessage("default value").call();
        } catch (IOException | GitAPIException e) {
            throw new RuntimeException(e);
        }
        return 1;
    }



    @Override
    public int committopath(String localPath, String agentName, String repoName, String branch, String path_,File file) {
        String path = localPath+File.separator+agentName+File.separator+repoName+path_;
        String filePath =path+File.separator+file.getName();
        try {
            FileCoverUtil.updateFile(filePath,file);
            File origin = new File(path);
            Git git = Git.open(origin);
            git.checkout().setCreateBranch(false).setName(branch).call();
            git.add().addFilepattern(".").call();
            Status status = git.status().call();
            if (!checkStatus(status)){
                return -1;
            }
            RmCommand rm = git.rm();
            for (String m: status.getMissing()){
                rm.addFilepattern(m).call();
                rm = git.rm();
                status = git.status().call();
            }
            for (String r: status.getRemoved()){
                rm.addFilepattern(r).call();
                rm = git.rm();
                status = git.status().call();
            }
            git.commit().setMessage("default value").call();
        } catch (IOException | GitAPIException e) {
            throw new RuntimeException(e);
        }
        return 1;

    }


    /**
     * 检查本地文件变更状态
     * @param status
     * @return
     */
    private boolean checkStatus(Status status){
        int add = status.getAdded().size();
        System.out.printf("新增%d个文件\n", add);
        int change = status.getChanged().size();
        System.out.printf("变更%d个文件\n", change);
        int remove = status.getRemoved().size();
        System.out.printf("删除%d个文件\n", remove);
        int miss = status.getMissing().size();
        System.out.printf("丢失%d个文件\n", miss);
        int modified = status.getModified().size();
        System.out.printf("修改%d个文件\n", modified);
        int conflict = status.getConflicting().size();
        System.out.printf("冲突%d个文件\n", conflict);
        int ignore = status.getIgnoredNotInIndex().size();
        System.out.printf("忽略%d个文件\n", ignore);
        if (add == 0 && change == 0 && remove == 0 && miss == 0 && modified ==0 && conflict == 0 && ignore == 0){
            System.out.println("未发生改变");
            return false;
        }
        return true;
    }


    /**
     * 返回所有提交版本的ID
     * @param agentName
     * @param repoName
     * @param branch
     * @return
     */
    @Override
    public List<RevCommit> getCommitsByBranch(String localPath, String agentName, String repoName, String branch) {
        List<RevCommit> commits = new ArrayList<>();
        String path = localPath+flash+agentName+flash+repoName;
        try {
            Git git = Git.open(new File(path));
            Repository repository = git.getRepository();
            Ref ref = repository.findRef(branch);
            RevWalk revWalk = new RevWalk(repository);
            revWalk.markStart(revWalk.parseCommit(ref.getObjectId()));
            for (RevCommit revCommit: revWalk){
                System.out.printf("The commit time:%s The commit ID:%s\n", DateParser.getCommitDate(revCommit.getCommitTime()), revCommit.getName());
                commits.add(revCommit);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return commits;
    }


    @Override
    public List<String> getContent(String path, String branch,String dirPath) {
        try {
            Git repository = Git.open(new File(path));
            Ref ref = repository.checkout().setName(branch).call();
            File f = new File(dirPath);
            return Arrays.stream(f.listFiles()).map(File::getName).filter(o->!o.equals(".git")).collect(Collectors.toList());
        } catch (GitAPIException | IOException e) {
            throw new RuntimeException(e);
        }
    }

}





