package com.example.demo.service;

import org.eclipse.jgit.api.Git;

public interface RepositoryService {

    Git initRepository (String path, String repositoryName);

    Git cloneRepository (String remotePath, String localPath, String repositoryName);

    Git loadLocalRepository(String path, String repositoryName);
}
