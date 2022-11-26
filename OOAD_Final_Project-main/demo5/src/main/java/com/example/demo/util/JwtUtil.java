package com.example.demo.util;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.UUID;



public class JwtUtil {

    private static long time = 1000*5;

    private static String signature = "Token";



    public static String createToken(String username,String password){
        JwtBuilder jwtBuilder = Jwts.builder();
        String jwtToken = jwtBuilder
                //header
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")
                //payload
                .claim("username", username)
                .claim("password", password)
                .setSubject("admin-test")
                .setExpiration(new Date(System.currentTimeMillis()+time))
                .setId(UUID.randomUUID().toString())
                //signature
                .signWith(SignatureAlgorithm.HS256, signature)
                .compact();
        return jwtToken;
    }



    public static String[] checkToken(String token){
        if(token == null){
            return null;
        }
        String[] strings = new String[2];
        try {

            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(signature).parseClaimsJws(token);
            Claims claims = claimsJws.getBody();
            strings[0] = (String) claims.get("username");
            strings[1] =(String) claims.get("password");

        } catch (Exception e) {
            return null;
        }
        return strings;
    }
}




