package com.example.demo.service.imp;

import com.example.demo.entity.Repository;
import com.example.demo.mapper.RepositoryMapper;
import com.example.demo.service.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RepositoryServiceImp implements RepositoryService {
    @Autowired
    RepositoryMapper repositoryMapper;

//    @Override
//    public int add(int version_id, String content, String comment, String commit_time){
//        return repositoryMapper.createNewRepo(new repository(version_id,content,comment,commit_time));
//    }

    @Override
    public void createNewRepo(String name, int AgentId, String content, String commit_time) {
        Repository repo = new Repository(name, 0, 0, AgentId, 1, "main", content, "the main branch", commit_time);
        repositoryMapper.createNewRepo(repo);
        repositoryMapper.createNewStaticRepo(repo);
        repositoryMapper.createNewBranch(repo);
    }
}
