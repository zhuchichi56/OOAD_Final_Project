package com.example.demo.service.imp;


import com.example.demo.service.RepositoryService;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Logger;

@Service
public class RepositoryServiceImp implements RepositoryService{



    @Override
    public Git initRepository(String path, String repositoryName) {
        File repository = new File(path + repositoryName);
        if(repository.exists()) {
            System.out.println("repo exists");
        }
        Git git = null;
        try {
            git = Git.init().setDirectory(repository).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }

        
        return git;
    }

    @Override
    public Git cloneRepository(String remotePathUrl, String localPath, String repositoryName) {
        Git git = null;
        File repository = new File(localPath + repositoryName);
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
    public Git loadLocalRepository(String path, String repositoryName) {
        Repository repository = null;
        try{
             repository = new FileRepositoryBuilder()
                    .setGitDir(Paths.get(path + repositoryName, ".git").toFile())
                    .build();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return Git.wrap(repository);
    }
}
