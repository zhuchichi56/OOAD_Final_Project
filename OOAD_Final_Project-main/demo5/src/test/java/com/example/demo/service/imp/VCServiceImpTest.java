package com.example.demo.service.imp;

import com.example.demo.entity.VC;
import com.example.demo.mapper.VCMapper;
import com.example.demo.service.VCService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class VCServiceImpTest {

    @Autowired
    VCService vcService;
    @Autowired
    VCMapper vcMapper;
    @Test
    void showRepoCVList() {
        System.out.println(vcService.showRepoVCList(2,"hello7","main"));
    }

    @Test
    void selectTest(){
        List<VC> text = vcMapper.selectVCbyFather(3);
        System.out.println(text.get(0));
    }
}








