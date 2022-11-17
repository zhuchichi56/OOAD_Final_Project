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
public class FileController {

    @Autowired
    CommitService commitService;
    String localpath = "/Users/zhuhe/Desktop/Jgit";






    @PostMapping("/RepoBrowser/{agentName}/{repoName}/{branch}/upload")
    public void upload(MultipartFile file, @PathVariable("agentName") String agentName,
                       @PathVariable("repoName") String repoName, @PathVariable("branch") String branch) throws IOException {
//        String fileRealName = file.getOriginalFilename();//获得原始文件名;
//        int pointIndex =  fileRealName.lastIndexOf(".");//点号的位置
//        String fileSuffix = fileRealName.substring(pointIndex);//截取文件后缀
//        String fileNewName = DateUtils.getNowTimeForUpload();//新文件名,时间戳形式yyyyMMddHHmmssSSS
//        String saveFileName = fileNewName.concat(fileSuffix);//新文件完整名（含后缀

        String filePath = "/Users/zhuhe/Desktop/RepoBrowser/" + agentName + "/" + repoName + "/" + branch + "/";
        System.out.println(filePath);



        File savedFile = new File(filePath);
        boolean isCreateSuccess = savedFile.createNewFile(); // 是否创建文件成功
        if (isCreateSuccess) {
            file.transferTo(savedFile);
//            commitService.commitFiles("/Users/zhuhe/Desktop/Jgit",agentName, repoName, branch, savedFile);
        }

    }











//    @GetMapping("/RepoBrowser/{agentName}/{repoName}/{branch}/upload")
//    public void returnjson(@PathVariable("agentName") String agentName,
//                       @PathVariable("repoName") String repoName,
//                           @PathVariable("branch") String branch) throws IOException {
////        String fileRealName = file.getOriginalFilename();//获得原始文件名;
////        int pointIndex =  fileRealName.lastIndexOf(".");//点号的位置
////        String fileSuffix = fileRealName.substring(pointIndex);//截取文件后缀
////        String fileNewName = DateUtils.getNowTimeForUpload();//新文件名,时间戳形式yyyyMMddHHmmssSSS
////        String saveFileName = fileNewName.concat(fileSuffix);//新文件完整名（含后缀
//
//        String filePath = "/Users/zhuhe/Desktop/RepoBrowser/" + agentName + "/" + repoName + "/" + branch + "/";
//        System.out.println(filePath);
//
//



//
//
//
//        File savedFile = new File(filePath);
//        boolean isCreateSuccess = savedFile.createNewFile(); // 是否创建文件成功
//        if (isCreateSuccess) {
//            file.transferTo(savedFile);
//            commitService.commitFiles(agentName, repoName, branch, savedFile);
//        }
//
//
//    }



//    @GetMapping("/RepoBrowser/{agentName}/{repoName}/{branch}/{path}")
//    public JsonResult<Map> getMap(@PathVariable("agentName") String agentName,
//                                  @PathVariable("repoName") String repoName,
//                                  @PathVariable("branch") String branch,
//                                  @PathVariable("path") String path
//                                  )
//    {
//
//
//
//        Map<String, Object> map = new HashMap<>(3);
////        User user = new User(1, "倪升武", null);
//
//        map.put("display", user);
//        map.put("branchList", "http://blog.itcodai.com");
//        map.put("itemList", null);
//        map.put("fileContent", 4153);
//
//
//
//
//        return new JsonResult<>(map);
//    }




    /*
    * 上传文件
    * */
    @PostMapping("/upload")
    public void Test(@RequestParam("file") MultipartFile[] multipartFile,
                     @RequestParam("UserName") String agentName,
                     @RequestParam("RepoName") String repoName,
                     @RequestParam("BranchName") String branch,
                     @RequestParam("FilePath") String path

    ) throws IOException {

        String filePath = "/Users/zhuhe/Desktop/RepoBrowser/" + agentName + "/" + repoName + "/" + branch + "/";

        List<String> names = new ArrayList<>(multipartFile.length);
        for (MultipartFile file : multipartFile) {
            String fileName = file.getOriginalFilename();
            System.out.println(fileName);

            File savedFile = new File("/Users/zhuhe/Desktop/RepoBrowser/"+ fileName);
//            boolean isCreateSuccess = savedFile.createNewFile(); // 是否创建文件成功
            if (!savedFile.getParentFile().exists()) {
                savedFile.getParentFile().mkdirs();
            }

            file.transferTo(savedFile);
//            commitService.commitFiles("/Users/zhuhe/Desktop/Jgit",agentName, repoName, branch, savedFile);

            //
        }

    }
}








