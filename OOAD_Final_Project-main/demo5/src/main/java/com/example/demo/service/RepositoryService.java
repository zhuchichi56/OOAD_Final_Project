package com.example.demo.service;

import org.eclipse.jgit.api.Git;

import java.io.File;

public interface RepositoryService {

    Git initRepository (String path,String agentName ,String repositoryName, int authority);

    boolean deleteRepository (String path, String agentName ,String repositoryName);

    String getRepoId(String agentName,String repoName );

    int checkRepoInfo(String agentName, String repoName);

    int setAuthority(String repoId, int authority);

//    Git   cloneRepository (String remotePath, String localPath, String agentName, String repositoryName);

//    Git forkRepository(String targetName, String targetRepoName,
//                       String localPath, String forkName, String forkRepoName);

    Git forkRepository(String localPath,String targetName, String targetRepoName,String targetBranch,
                        String forkName, String forkRepoName);

    Git loadLocalRepository(String path, String agentName , String repositoryName);
}
