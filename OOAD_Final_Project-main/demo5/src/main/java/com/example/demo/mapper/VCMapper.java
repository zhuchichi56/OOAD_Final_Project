package com.example.demo.mapper;


import com.example.demo.entity.VC;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface VCMapper {
    //更新VC表
    int createNewVc(@Param("fatherId") int fatherId,@Param("childId") int childId );
    List<VC> selectVCbyFather(@Param("rootRepoId") int rootId);

    VC selectVCbyChild(int childId);

    int updateVC(@Param("vc") VC vc, @Param("newFatherId") int newFatherId, @Param("newChildId")int newChildId);

    int deleteVC(VC vc);
}








