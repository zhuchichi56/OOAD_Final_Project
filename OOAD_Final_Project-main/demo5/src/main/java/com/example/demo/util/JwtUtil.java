package com.example.demo.util;
import com.alibaba.fastjson.JSONObject;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import io.jsonwebtoken.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.UUID;



public class JwtUtil {

    private static long time = 1000*60*60*24;

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






    public static boolean sendNullResponce(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            JSONObject res = new JSONObject();
            res.put("status", false);
            res.put("message", "没有token");
            System.out.println("token为空");
            response.setStatus(401);
            out = response.getWriter();
            out.append(res.toString());

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
        }
        return false;
    }

    public static boolean sendWrongResponce(HttpServletResponse response,String message) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            JSONObject res = new JSONObject();
            res.put("status", false);
            res.put("message", message);
//            System.out.println("token验证不能通过");
            response.setStatus(401);
            out = response.getWriter();
            out.append(res.toString());

        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
        }
        return false;
    }

    public static boolean sendSuccessResponce(HttpServletResponse response) throws IOException {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try {
            JSONObject res = new JSONObject();
            res.put("status", true);
            res.put("message", "token验证通过");
            System.out.println("token验证通过");
            response.setStatus(200);
            out = response.getWriter();
            out.append(res.toString());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(500);
            return false;
        }
    }





}





