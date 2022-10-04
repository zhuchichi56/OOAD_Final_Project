package com.example.demo.service.imp;

import com.example.demo.service.VCService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class VCServiceImpTest {

    @Autowired
    VCService vcService;
    @Test
    void showRepoCVList() {
        System.out.println(vcService.showRepoCVList(2,"hello7","main"));
    }
}








