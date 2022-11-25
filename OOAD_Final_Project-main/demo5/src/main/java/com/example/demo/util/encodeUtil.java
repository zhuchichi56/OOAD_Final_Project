package com.example.demo.util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class encodeUtil {
    public static String hash(String... params){
        StringBuilder sb = new StringBuilder();
        try {
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            for (String s : params){
                sb.append(s);
            }
            byte[] messageDigest = sha.digest(sb.toString().getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            StringBuilder hashText = new StringBuilder(no.toString(16));
            while (hashText.length() < 32) {
                hashText.insert(0, "0");
            }
            return hashText.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
