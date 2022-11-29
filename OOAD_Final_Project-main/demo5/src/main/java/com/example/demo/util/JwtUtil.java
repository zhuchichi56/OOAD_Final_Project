package com.example.demo.util;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.UUID;



public class JwtUtil {

    private static long time = 1000*24*60*60;

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
        } catch (SignatureVerificationException e) {
            System.out.println("无效签名！ 错误 ->"+ e);
//            log.error("无效签名！ 错误 ->", e);
            return null;
        } catch (TokenExpiredException e) {
            System.out.println("token过期！ 错误 ->"+ e);
//            log.error("token过期！ 错误 ->", e);
            return null;
        } catch (AlgorithmMismatchException e) {
            System.out.println("token算法不一致！ 错误 ->"+ e);
//            log.error("token算法不一致！ 错误 ->", e);
            return null;
        } catch (Exception e) {
            System.out.println("token无效！ 错误 ->"+ e);
//            log.error("token无效！ 错误 ->", e);
            return null;
        }
        return strings;
    }
}





