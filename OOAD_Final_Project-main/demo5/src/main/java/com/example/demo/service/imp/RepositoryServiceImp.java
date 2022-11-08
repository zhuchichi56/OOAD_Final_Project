package com.example.demo.service.imp;


import com.example.demo.entity.Repo;
import com.example.demo.mapper.RepositoryMapper;
import com.example.demo.service.RepositoryService;
import com.example.demo.util.encodeUtil;
import org.apache.ibatis.jdbc.Null;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;

@Service
public class RepositoryServiceImp implements RepositoryService{
    @Autowired
    RepositoryMapper repositoryMapper;


    @Override
    public Git initRepository(String path, String agentName, String repositoryName) {
        File repository = new File(path+ "\\" + agentName +"\\" + repositoryName);
        if (repositoryMapper.getRepoId(agentName,repositoryName) == null)
            repositoryMapper.createNewRepository(encodeUtil.hash(agentName,repositoryName), new Repo(agentName, repositoryName));
        if(repository.exists()) {
            System.out.println("repo exists");
        }
        Git git = null;
        try {
            git = Git.init().setDirectory(repository).call();
            git.commit().setMessage("Initial commit").call();
            git.checkout().setName("master").call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }

        
        return git;
    }

    /**
     * 检测有无这个仓库的存在
     * @param agentName
     * @param repoName
     * @return
     */
    @Override
    public int checkRepoInfo(String agentName, String repoName) {
        if (!repositoryMapper.getRepoId(agentName,repoName).equals(""))
            return 1;
        return 0;
    }

    @Override
    public Git cloneRepository(String remotePathUrl, String localPath, String agentName, String repositoryName) {
        Git git = null;
        File repository = new File(localPath+ "\\" + agentName +"\\" + repositoryName);
        if(repository.exists()) {
            System.out.println("repo exists");
        }
        try {
            git = Git.cloneRepository()
                    .setURI(remotePathUrl)
                    .setDirectory(repository)
                    .call();

        } catch (GitAPIException e) {
            e.printStackTrace();
        }

        return git;
    }

    @Override
    public Git loadLocalRepository(String path, String agentName ,String repositoryName) {
        Repository repository = null;
        try{
             repository = new FileRepositoryBuilder()
                    .setGitDir(Paths.get(path+ "\\" + agentName +"\\" + repositoryName, ".git").toFile())
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return Git.wrap(repository);
    }
}
