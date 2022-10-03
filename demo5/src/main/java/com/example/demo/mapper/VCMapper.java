package com.example.demo.mapper;

import com.example.demo.entity.VC;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VCMapper {
    Integer getChildRepoId(@Param("fatherRepoId") int fatherRepoId);
    int createNewVC(@Param("VC")VC vc);
}
