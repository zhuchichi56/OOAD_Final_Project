package com.example.demo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateParser {
    public static String printTime(int commitTime){
        SimpleDateFormat simpleTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = String.valueOf(commitTime);
        long timeStamp = Long.parseLong(time)*1000;
        return simpleTimeFormat.format(new Date(timeStamp));
    }
}
