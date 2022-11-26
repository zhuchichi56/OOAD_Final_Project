package com.example.demo.controller;


//import com.mysql.cj.protocol.Message;
import org.springframework.web.bind.annotation.RestController;
import com.example.demo.service.CommitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class upload {


    String localPath = "/Users/zhuhe/Desktop/Test";
    @RequestMapping("/upload")
    public int upload(@RequestParam("type")int type, @RequestParam("file") MultipartFile[] file){
        for(MultipartFile f:file){
            try {
                String targetpath = localPath + "/"+f.getOriginalFilename();
                File file1 = new File(targetpath);
                file1.mkdirs();
                file1.createNewFile();
                f.transferTo(file1);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return 1;
    }


}
