package com.example.demo.mapper;


import com.example.demo.entity.Agent;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper
public interface AgentMapper {

    int createAgent(@Param("agent") Agent agent);

    int updateUserName(@Param("oldName") String oldName,@Param("newName") String newName);

    int updateUserPassword(@Param("name") String name,@Param("password") String password);

    int checkUser(@Param("name") String name, @Param("password") String password);

    int checkUserName(@Param("name") String name);

    int deleteUser(@Param("name") String name);

}




