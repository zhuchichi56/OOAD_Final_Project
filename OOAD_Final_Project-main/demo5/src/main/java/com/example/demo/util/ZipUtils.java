package com.example.demo.util;
import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;



public class ZipUtils {


    public static void packageZip(String filesPath,String targetpath) throws Exception {

        File file = new File(filesPath);   //需要压缩的文件夹
        File zipFile = new File(targetpath+".zip");  //放于和需要压缩的文件夹同级目录
        ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFile));
        isDirectory(file,zipOut,"",true);   //判断是否为文件夹
        zipOut.close();
    }


    public static void isDirectory(File file, ZipOutputStream zipOutputStream, String filePath, boolean flag) throws IOException {
        //判断是否为问加减
        if(file.isDirectory()){
            File[] files = file.listFiles();  //获取该文件夹下所有文件(包含文件夹)
            filePath = flag==true?file.getName():filePath + File.separator + file.getName();   //首次为选中的文件夹，即根目录，之后递归实现拼接目录
            for(int i = 0; i < files.length; ++i){
                //判断子文件是否为文件夹
                if(files[i].isDirectory()){
                    //进入递归,flag置false 即当前文件夹下仍包含文件夹
                    isDirectory(files[i],zipOutputStream,filePath,false);
                }else{
                    //不为文件夹则进行压缩
                    InputStream input = new FileInputStream(files[i]);
                    zipOutputStream.putNextEntry(new ZipEntry(filePath+File.separator+files[i].getName()));
                    int temp = 0;
                    while((temp = input.read()) != -1){
                        zipOutputStream.write(temp);
                    }
                    input.close();
                }
            }
        }else{
            //将子文件夹下的文件进行压缩
            InputStream input = new FileInputStream(file);
            zipOutputStream.putNextEntry(new ZipEntry(file.getPath()));
            int temp = 0;
            while((temp = input.read()) != -1){
                zipOutputStream.write(temp);
            }
            input.close();
        }
    }
}



