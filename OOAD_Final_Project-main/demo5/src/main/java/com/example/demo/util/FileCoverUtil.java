package com.example.demo.util;

import java.io.*;
import java.util.List;

public class FileCoverUtil {

    public static void updateFile(String path, File file){
        File origin = new File(path);
        deleteFile(origin);
        coverFile(path,file);
    }
    private static void deleteFile(File file){
        if (file.getName().equals(".git")){
            return;
        }
        if (file.isDirectory()){
            File[] list = file.listFiles();
            for (File f: list){
                deleteFile(f);
            }
        }
        file.delete();
    }

    private static void coverFile(String path, File file){
        if (file.getName().equals(".git")){
            return;
        }
        if (file.isDirectory()){
            File[] list = file.listFiles();
            String newPath = path+'\\'+file.getName();
            File coverFile = new File(newPath);
            boolean b = coverFile.mkdir();
            for (File f: list){
                coverFile(newPath,f);
            }
        } else {
            try {
                FileReader fr = new FileReader(file);
                char[] buffer = new char[999999];
                int len = fr.read(buffer);
                FileWriter fw = new FileWriter(path+'\\'+file.getName());
                fw.write(buffer);
                fr.close();
                fw.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
