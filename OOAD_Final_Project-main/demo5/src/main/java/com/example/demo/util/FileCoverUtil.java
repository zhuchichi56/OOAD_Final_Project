package com.example.demo.util;

import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;

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
                BufferedImage image = ImageIO.read(file);
                if (image != null){
                    String location = path + '\\' + file.getName();
                    ImageIO.write(image,getImageType(file), new File(location));
                } else {
                    FileReader fr = new FileReader(file);
                    char[] buffer = new char[999999];
                    int len = fr.read(buffer);
                    FileWriter fw = new FileWriter(path + '\\' + file.getName());
                    fw.write(buffer);
                    fr.close();
                    fw.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static String getImageType(File file){
        String type = file.getName().split("\\.")[1];
        if (type.equals("png")){
            return "PNG";
        }
        return "JPEG";
    }
}
