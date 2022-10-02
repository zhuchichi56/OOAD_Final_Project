package com.example.demo.service.imp;

import com.example.demo.entity.repository;
import com.example.demo.mapper.repositoryMapper;
import com.example.demo.service.repositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class repositoryServiceImp implements repositoryService {
    @Autowired
    repositoryMapper repositoryMapper;

    @Override
    public int add(int version_id, String content, String comment, String commit_time){
        return repositoryMapper.createNewRepo(new repository(version_id,content,comment,commit_time));
    }
}
