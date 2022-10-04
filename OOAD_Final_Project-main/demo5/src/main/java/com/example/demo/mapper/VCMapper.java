package com.example.demo.mapper;


import com.example.demo.entity.VC;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;



@Mapper
public interface VCMapper {
    //更新VC表
    int createNewVc(@Param("fatherId") int FatherId,@Param("childId") int ChildId );
    VC selectVCbyChild(@Param("childRepoId") int ChildId);




}








