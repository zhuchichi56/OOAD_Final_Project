package com.example.demo.controller;


//import com.mysql.cj.protocol.Message;
import com.example.demo.mapper.AgentMapper;
import com.example.demo.mapper.ForkRepoMapper;
import com.example.demo.mapper.RepositoryMapper;
import com.example.demo.mapper.WatchRepoMapper;
import com.example.demo.service.*;
import com.example.demo.util.FileCoverUtil;
import com.example.demo.util.ZipUtils;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@RestController
@RequestMapping(value = "/RepoBrowser")
public class uploadController {




    String localPath = "/Users/zhuhe/Desktop/Jgit";

    String testDirectory = "/Users/zhuhe/Desktop/Jgit.md";

    String temppath = "/Users/zhuhe/Temp";



    @Autowired
    RepositoryService repositoryService;

    @Autowired
    RepositoryMapper repositoryMapper;

    @Autowired
    ForkRepoMapper forkRepoMapper;

    @Autowired
    WatchRepoMapper watchRepoMapper;

    @Autowired
    AgentService agentService;

    @Autowired
    IssueService issueService;

    @Autowired
    BranchService branchService;

    @Autowired
    CommitService commitService;

    @Autowired
    AgentMapper agentMapper;





    @RequestMapping("/{agentName}/{repoName}/{branchName}/{path}/upload/{token_name}")
    public int upload_mutil(
            @PathVariable("agentName")String agentName,
            @PathVariable("repoName")String repoName,
            @PathVariable("branchName") String branchName,
            @PathVariable("path") String path,
            @RequestParam("file") MultipartFile[] file){



        String basePath = "";
        String[] splitPath = path.split("_");
        for (int i = 1; i < splitPath.length; i++) {
            basePath+=File.separator;
            basePath+=splitPath[i];
        }


        String[] name = file[0].getOriginalFilename().split("/");
        for(MultipartFile f:file){
            try {
                String targetpath = temppath + "/"+f.getOriginalFilename();
                System.out.println(targetpath);
                File file1 = new File(targetpath);

                file1.mkdirs();

                file1.createNewFile();

                f.transferTo(file1);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        System.out.println(name[0]);
        File temp = new File(temppath+File.separator+name[0]);
        commitService.committopath(localPath,agentName,repoName,branchName,basePath,temp);

        FileCoverUtil.deleteFolder(temp);

        return 1;
    }



    @GetMapping("/{agentName}/{repoName}/{branch}/download")
    public String fileDownLoad(HttpServletResponse response,
                               @PathVariable("agentName") String agentName,
                               @PathVariable("repoName") String repoName,
                               @PathVariable("branch") String branch

    ) throws Exception {


        String sourcePath = localPath+File.separator+agentName+File.separator+repoName;


        String zipPath = localPath+File.separator+agentName+File.separator+repoName+".zip";

        ZipUtils.packageZip(sourcePath ,sourcePath );

        File file = new File(zipPath);
        try {
            if(!file.exists()){
                return "下载文件不存在";
            }
            String filename  = file.getName();
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition",
                    "attachment; filename=" + new String(filename.getBytes("ISO8859-1"), "UTF-8"));
            response.setContentLength((int) file.length());
            response.setContentType("application/zip");// 定义输出类型
            FileInputStream fis = new FileInputStream(file);
            BufferedInputStream buff = new BufferedInputStream(fis);
            byte[] b = new byte[1024];// 相当于我们的缓存
            long k = 0;// 该值用于计算当前实际下载了多少字节
            OutputStream myout = response.getOutputStream();// 从response对象中得到输出流,准备下载
            // 开始循环下载
            while (k < file.length()) {
                int j = buff.read(b, 0, 1024);
                k += j;
                myout.write(b, 0, j);
            }
            myout.flush();
            buff.close();
            file.delete();
        } catch (IOException e) {
            return "下载失败";
        }
        return "下载成功";
    }







}
