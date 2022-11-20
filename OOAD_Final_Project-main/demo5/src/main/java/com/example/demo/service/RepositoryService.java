package com.example.demo.service;

import org.eclipse.jgit.api.Git;

import java.io.File;

public interface RepositoryService {

    Git initRepository (String path,String agentName ,String repositoryName, int authority);

    boolean deleteRepository (String path, String agentName ,String repositoryName);

    int checkRepoInfo(String agentName, String repoName);

    int updateAuthority(String repoId, int authority);

    Git cloneRepository (String remotePath, String localPath, String agentName, String repositoryName);

    Git loadLocalRepository(String path, String agentName ,String repositoryName);
}
