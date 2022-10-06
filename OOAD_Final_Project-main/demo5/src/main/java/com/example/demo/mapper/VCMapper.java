package com.example.demo.mapper;


import com.example.demo.entity.VC;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface VCMapper {
    //更新VC表
    int createNewVc(@Param("fatherId") int FatherId,@Param("childId") int ChildId );
    List<VC> selectVCbyFather(@Param("rootRepoId") int RootId);

    VC selectVCbyChild(int ChildId);

    int updateVC(@Param("vc") VC vc,int NewFatherId,int NewChildId);

    int deleteVC(VC vc);
}








