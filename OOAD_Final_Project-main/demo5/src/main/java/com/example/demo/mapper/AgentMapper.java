package com.example.demo.mapper;


import com.example.demo.entity.Agent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;



@Mapper
public interface AgentMapper {

    int  createAgent(@Param("agent") Agent agent);

    int updateUserName(@Param("oldName") String oldName,@Param("newName") String newName);

    int updateUserIcon(@Param("name") String name,@Param("newIconUrl") String url);

}




