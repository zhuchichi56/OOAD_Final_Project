package com.example.demo.service.imp;

import com.example.demo.entity.Branch;
import com.example.demo.entity.RepoContent;
import com.example.demo.entity.VC;
import com.example.demo.mapper.*;
import com.example.demo.service.VCService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class VCServiceImp implements VCService {


    @Autowired
    AgentMapper agentMapper;
    @Autowired
    BranchMapper branchMapper;
    @Autowired
    RepoContentMapper repoContentMapper;
    @Autowired
    StaticRepoMapper staticRepoMapper;
    @Autowired
    VCMapper vcMapper;


    @Override
    public ArrayList<RepoContent> showRepoCVList(int AgentId, String RepoName, String BranchName) {

        Branch branch = branchMapper.selectBranchAll(AgentId,RepoName,BranchName);

        int currentRepoId = branch.getCurrentRepoId();
        int rootRepoId = branch.getRootRepoId();
        int tempRepoId = currentRepoId;
        ArrayList<RepoContent> repoContentsList = new ArrayList<>();
        while(tempRepoId!=rootRepoId){
            repoContentsList.add(repoContentMapper.selectRepoContent(tempRepoId));
            VC vc = vcMapper.selectVCbyChild(tempRepoId);
            tempRepoId = vc.getFatherRepoId();
        }
        repoContentsList.add(repoContentMapper.selectRepoContent(rootRepoId));
        return repoContentsList;
    }
}
