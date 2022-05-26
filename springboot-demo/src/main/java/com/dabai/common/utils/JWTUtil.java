package com.dabai.common.utils;

import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * @author
 * @create 2022-05-18 19:34
 */
public class JWTUtil {
    private static final String JWT_TOKEN = "123456_dabai_!@##$$";
    private static final long VALID_TIME = 1000*60*60*24;   //有效时间为一天

    public static String createToken(Long userId) {
        JwtBuilder jwtBuilder = Jwts.builder();
        //链式编程
        String token = jwtBuilder
                //header, {"type":"JWT","alg":"HS256"} 固定
                .setHeaderParam("type","JWT")
                .setHeaderParam("alg","HS256")
                //payLoad,存放信息，比如，用户id，过期时间等等，可以被解密，不能存放敏感信息
                .claim("userId",userId)
                .setIssuedAt(new Date())    //设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + VALID_TIME))
                //signature: header+payLoad+密钥 加密得到，只有密钥不丢失，就是安全的。
                .signWith(SignatureAlgorithm.HS256, JWT_TOKEN)
                //将header, payLoad, signature 三部分用 . 拼接起来
                .compact();
        return token;
    }

    public static Map<String, Object> checkToken(String token) {
        Jwt parse = Jwts.parser().setSigningKey(JWT_TOKEN).parse(token);
        return (Map<String, Object>) parse.getBody();
    }
}
