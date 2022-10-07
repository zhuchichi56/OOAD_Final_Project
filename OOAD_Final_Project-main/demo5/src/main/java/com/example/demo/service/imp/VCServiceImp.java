package com.example.demo.service.imp;

import com.example.demo.entity.Branch;
import com.example.demo.entity.RepoContent;
import com.example.demo.entity.VC;
import com.example.demo.mapper.*;
import com.example.demo.service.VCService;
import com.example.demo.util.TransactionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import java.util.ArrayList;
import java.util.List;


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

    @Autowired
    private DataSourceTransactionManager dataSourceTransactionManager;

    /**
     * 查找历史记录时，从叶子节点向上搜索
     * */

    @Override
    public ArrayList<RepoContent> showRepoVCList(int agentId, String repoName, String branchName) {
        TransactionStatus transaction = TransactionUtil.getTransaction(dataSourceTransactionManager);
        ArrayList<RepoContent> repoContentsList = new ArrayList<>();
        try {
            Branch branch = branchMapper.selectBranchAll(agentId,repoName,branchName);
            int currentRepoId = branch.getCurrentRepoId();
            int rootRepoId = branch.getRootRepoId();
            int tempRepoId = currentRepoId;
            while(tempRepoId!=rootRepoId){
                repoContentsList.add(repoContentMapper.selectRepoContent(tempRepoId));
                VC vc = vcMapper.selectVCbyChild(tempRepoId);
                tempRepoId = vc.getFatherRepoId();
            }
            repoContentsList.add(repoContentMapper.selectRepoContent(rootRepoId));
            dataSourceTransactionManager.commit(transaction);
        } catch (Exception e){
            e.printStackTrace();
            dataSourceTransactionManager.rollback(transaction);
        }
        return repoContentsList;
    }


    /**
     * 回滚版本，将vc链的结构改变，将想要回滚的版本放置到叶子节点处，并将最新ID更新为回滚版本
     * **/
    @Override
    public void rollBack(int oldRepoId, int agentId, String repoName, String branchName) {
        TransactionStatus transaction = TransactionUtil.getTransaction(dataSourceTransactionManager);
        try {
            Branch branch = branchMapper.selectBranchAll(agentId,repoName,branchName);
            //以回滚版本为孩子节点的VC
            VC vc1 = vcMapper.selectVCbyChild(oldRepoId);
            //以回滚版本为父亲节点的VC
            int currentRepoId = branch.getCurrentRepoId();
            //找到处于该branch的子节点
            VC vc2 = vcMapper.selectVCbyFather(oldRepoId)
                    .stream().filter(o->DFS(o.getChildRepoId(),currentRepoId)==1).findFirst().get();
            vcMapper.updateVC(vc2,vc1.getFatherRepoId(),vc2.getChildRepoId());
            vcMapper.deleteVC(vc1);
            vcMapper.createNewVc(currentRepoId,oldRepoId);
            branchMapper.updateBranchCurrentId(agentId,repoName,branchName,oldRepoId);
            dataSourceTransactionManager.commit(transaction);
        }  catch (Exception e){
            e.printStackTrace();
            dataSourceTransactionManager.rollback(transaction);
        }
    }

    private int DFS(int startId, int endId){
        if (startId == endId){
            return 1;
        }
        int ans = 0;
        for (VC c : vcMapper.selectVCbyFather(startId)){
            ans +=DFS(c.getChildRepoId(),endId);
        }
        return ans;
    }
}
