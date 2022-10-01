package com.example.demo.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {
//    @Insert("insert into user values(#{id},#{username},#{password},#{birthday})")
//    int add(User user);
//
//    @Update("update user set username=#{username},password=#{password},birthday=#{birthday} where id=#{id}")
//    int update(User user);
//
//    @Delete("delete from user where id=#{id}")
//    int delete(int id);
//
//    @Select("select * from user where id=#{id}")
//    User findById(int id);
//
//    @Select("select * from user")
//    List<User> getAll();
}
