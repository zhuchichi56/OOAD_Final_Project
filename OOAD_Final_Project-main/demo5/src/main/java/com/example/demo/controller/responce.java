package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;

@RestController
public class responce {
    String localPath = "/Users/zhuhe/Desktop/编译原理Project总结.md";
    @GetMapping("/download")
    public String fileDownLoad(HttpServletResponse response,
                               @RequestParam("fileName") String fileName){
        File file = new File(localPath);
        if(!file.exists()){
            return "下载文件不存在";
        }
        response.reset();
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("utf-8");
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName );

        try(BufferedInputStream bis = new BufferedInputStream(Files.newInputStream(file.toPath()));) {
            byte[] buff = new byte[1024];
            OutputStream os  = response.getOutputStream();
            int i = 0;
            while ((i = bis.read(buff)) != -1) {
                os.write(buff, 0, i);
                os.flush();
            }
        } catch (IOException e) {
//            log.error("{}",e);
            return "下载失败";
        }
        return "下载成功";
    }
}
