package com.example.demo.controller;

import com.example.demo.service.CommitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping(value = "/RepoBrowser")
public class FileController {

    @Autowired
    CommitService commitService;
    String localPath = "/Users/zhuhe/Desktop/Jgit";


//    @PostMapping("/RepoBrowser/{agentName}/{repoName}/{branch}/upload")
//    public void upload(MultipartFile file,
//                       ) throws IOException {
//
//        String filePath = "/Users/zhuhe/Desktop/RepoBrowser/" + agentName + "/" + repoName + "/" + branch + "/";
//        System.out.println(filePath);
//
//
//
//        File savedFile = new File(filePath);
//        boolean isCreateSuccess = savedFile.createNewFile(); // 是否创建文件成功
//        if (isCreateSuccess) {
//            file.transferTo(savedFile);
////            commitService.commitFiles("/Users/zhuhe/Desktop/Jgit",agentName, repoName, branch, savedFile);
//        }
//
//    }




    @RequestMapping("/{agentName}/{repoName}/{branch}/upload")
    public int upload(@RequestParam("type")int type,
                      @PathVariable("agentName") String agentName,
                      @PathVariable("repoName") String repoName,
                      @PathVariable("branch") String branch,
                      @RequestParam("file") MultipartFile[] file){
        String temppath =  "/Users/zhuhe/Desktop/Temp";

        for(MultipartFile f:file){
            try {
                String targetpath = temppath + File.separator+f.getOriginalFilename();
                File file1 = new File(targetpath);
                file1.mkdirs();
                file1.createNewFile();
                f.transferTo(file1);

            }catch (Exception e){
                e.printStackTrace();
            }
        }


//        File file_ = new File(temppath+ File.separator);
//        commitService.commitFiles(localPath, agentName, repoName, branch,file_);
        return 1;
    }


}








