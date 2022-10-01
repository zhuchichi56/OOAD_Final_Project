package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController {


    @Autowired  //自动注入，Spring会自动实例化UserMapper，并注入此属性中（依赖注入）
    private UserMapper userMapper;

    //  查询所有用户：localhost:8080/findAll

    @GetMapping("/findAll")
    public List findAll(){
        List<User> userList = userMapper.selectList(null);
        return userList;
    }




}
