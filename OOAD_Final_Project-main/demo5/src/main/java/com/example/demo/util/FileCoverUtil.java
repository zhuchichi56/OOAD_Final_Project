package com.example.demo.util;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

public class FileCoverUtil {

    public static void updateFile(String path, File file) {
        File origin = new File(path);
        if (origin.exists()) {
            if (origin.isDirectory()) {
                for (File f : origin.listFiles()) {
                    deleteFile(f);
                }
            } else {
                deleteFile(origin);
            }
        } else {
            if (file.isDirectory()) {
                origin.mkdirs();
            } else {
                origin.getParentFile().mkdirs();
            }
        }
        if (!file.isDirectory()) {
            coverFile(origin.getParentFile().getAbsolutePath(), file);
        } else {
            for (File f : file.listFiles()) {
                coverFile(path, f);
            }
        }
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
                    ImageIO.write(image, getImageType(file), new File(location));
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
    private static byte[] readInputStreamAt(FileInputStream fis, long skipLength, int length) throws IOException
    {
        byte[] buf = new byte[length];
        fis.skip(skipLength);  //
        int read = fis.read(buf,0,length);
        return buf;
    }
    private static String getImageType(File file) throws IOException {

        FileInputStream fis = null;
        try {
            if(!file.exists() || file.isDirectory() || file.length()<8) {
                throw new IOException("the file ["+file.getAbsolutePath()+"] is not image !");
            }

            fis= new FileInputStream(file);
            byte[] bufHeaders = readInputStreamAt(fis,0,8);
            if(isJPEGHeader(bufHeaders))
            {
                long skiplength = file.length()-2-8; //第一次读取时已经读了8个byte,因此需要减掉
                byte[] bufFooters = readInputStreamAt(fis, skiplength, 2);
                if(isJPEGFooter(bufFooters))
                {
                    return "jpeg";
                }
            }
            if(isPNG(bufHeaders))
            {
                return "png";
            }
            if(isGIF(bufHeaders)){

                return "gif";
            }
            if(isWEBP(bufHeaders))
            {
                return "webp";
            }
            if(isBMP(bufHeaders))
            {
                return "bmp";
            }
            if(isICON(bufHeaders))
            {
                return "ico";
            }
            throw new IOException("the image's format is unkown!");

        } catch (FileNotFoundException e) {
            throw e;
        }finally{
            try {
                if(fis!=null) fis.close();
            } catch (Exception e) {
            }
        }

    }
    private static boolean isBMP(byte[] buf){
        byte[] markBuf = "BM".getBytes();  //BMP图片文件的前两个字节
        return compare(buf, markBuf);
    }

    private static boolean isICON(byte[] buf) {
        byte[] markBuf = {0, 0, 1, 0, 1, 0, 32, 32};
        return compare(buf, markBuf);
    }
    private static boolean isWEBP(byte[] buf) {
        byte[] markBuf = "RIFF".getBytes(); //WebP图片识别符
        return compare(buf, markBuf);
    }

    private static boolean isGIF(byte[] buf) {

        byte[] markBuf = "GIF89a".getBytes(); //GIF识别符
        if(compare(buf, markBuf))
        {
            return true;
        }
        markBuf = "GIF87a".getBytes(); //GIF识别符
        if(compare(buf, markBuf))
        {
            return true;
        }
        return false;
    }


    private static boolean isPNG(byte[] buf) {

        byte[] markBuf = {(byte) 0x89,0x50,0x4E,0x47,0x0D,0x0A,0x1A,0x0A};

        return compare(buf, markBuf);
    }

    private static boolean isJPEGHeader(byte[] buf) {
        byte[] markBuf = {(byte) 0xff, (byte) 0xd8}; //JPEG开始符

        return compare(buf, markBuf);
    }

    private static boolean isJPEGFooter(byte[] buf)//JPEG结束符
    {
        byte[] markBuf = {(byte) 0xff, (byte) 0xd9};
        return compare(buf, markBuf);
    }
    private static boolean compare(byte[] buf, byte[] markBuf) {
        for (int i = 0; i < markBuf.length; i++) {
            byte b = markBuf[i];
            byte a = buf[i];

            if(a!=b){
                return false;
            }
        }
        return true;
    }
}
