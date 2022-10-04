package com.example.demo.mapper;


import com.example.demo.entity.Agent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;



@Mapper
public interface AgentMapper {

    int  createAgent(@Param("agent") Agent agent);

}




