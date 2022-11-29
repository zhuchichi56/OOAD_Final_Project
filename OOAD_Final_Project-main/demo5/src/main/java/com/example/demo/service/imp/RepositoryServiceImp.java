package com.example.demo.service.imp;


import com.example.demo.entity.Repo;
import com.example.demo.mapper.ContributorMapper;
import com.example.demo.mapper.RepositoryMapper;
import com.example.demo.mapper.StarRepoMapper;
import com.example.demo.service.RepositoryService;
import com.example.demo.util.FileCoverUtil;
import com.example.demo.util.encodeUtil;
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

    @Autowired
    ContributorMapper contributorMapper;

    @Autowired
    StarRepoMapper starRepoMapper;
    public static String flash = "/";



    @Override
    public Git initRepository(String path, String agentName, String repositoryName, int authority) {
        File repository = new File(path+ File.separator + agentName + File.separator + repositoryName);
        String repoId = encodeUtil.hash(agentName,repositoryName);
        if (repositoryMapper.getRepoId(agentName,repositoryName) == null) {
            repositoryMapper.createNewRepository(repoId, new Repo(agentName, repositoryName, authority, repoId));

        }
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

    @Override
    public boolean deleteRepository(String path, String agentName ,String repositoryName) {

        File repoPath = new File(path + File.separator + agentName + File.separator + repositoryName);
        String repoId = repositoryMapper.getRepoId(agentName, repositoryName);
        repositoryMapper.deleteRepository(repoId);
        return FileCoverUtil.deleteFolder(repoPath);
    }


    @Override
    public String getRepoId(String agentName, String repoName) {
        return repositoryMapper.getRepoId(agentName,repoName);
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
    public int setAuthority(String repoId, int authority) {
        return repositoryMapper.setAuthority(authority,repoId);
    }



    public Git forkRepository(String localPath,String targetName, String targetRepoName, String targetBranchName,
                              String forkName, String forkRepoName) {

        Git git = null;



        String url = localPath+ File.separator + targetName +File.separator +targetRepoName;
        File forkrepository = new File(localPath+ File.separator + forkName +File.separator + forkRepoName);

        if(forkrepository.exists()) {
            System.out.println("repo exists");
        }
        if(repositoryMapper.getRepoById(repositoryMapper.getRepoId(forkName,forkRepoName))==null){
            String repoId = repositoryMapper.getRepoId( targetName, targetRepoName);
            Repo remoteRepo = repositoryMapper.getRepoById(repoId);
            repositoryMapper.createNewRepository(encodeUtil.hash(forkName,forkRepoName),
                    new Repo(forkName,forkRepoName, remoteRepo.getAuthority(),
                            repositoryMapper.getRepoId(targetName,targetRepoName)));
        }
        try {
            git = Git.cloneRepository()
                    .setURI(url)
                    .setDirectory(forkrepository)
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
                    .setGitDir(Paths.get(path+ File.separator+ agentName + File.separator + repositoryName, ".git").toFile())
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return Git.wrap(repository);
    }



}






