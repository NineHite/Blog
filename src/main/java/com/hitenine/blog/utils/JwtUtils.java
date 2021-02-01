package com.hitenine.blog.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;

/**
 * Json Web Token 工具类
 *
 * @author Hitenine
 */
public class JwtUtils {

    //盐值
    private static String key = "ad128433d8e3356e7024009bf6add2ab";

    //单位是毫秒
    // private static long ttl = Constants.TimeValueInMillions.HOUR_2;//2个小时
    private static long ttl = 2 * 60 * 60 * 1000;//2个小时

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        JwtUtils.key = key;
    }

    public long getTtl() {
        return ttl;
    }

    public void setTtl(long ttl) {
        JwtUtils.ttl = ttl;
    }

    /**
     * @param claims 载荷内容
     * @param ttl    有效时长
     * @return
     */
    public static String createToken(Map<String, Object> claims, long ttl) {
        JwtUtils.ttl = ttl;
        return createToken(claims);
    }

    /**
     *
     * @param userId
     * @param ttl 单位秒
     * @return
     */
    public static String createRefreshToken(String userId, long ttl) {
        long nowMillis = System.currentTimeMillis() * 1000;
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder().setId(userId)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, key);
        if (ttl > 0) {
            builder.setExpiration(new Date(nowMillis + ttl));
        }
        return builder.compact();
    }

    /**
     * @param claims 载荷
     * @return token
     */
    public static String createToken(Map<String, Object> claims) {

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, key);

        if (claims != null) {
            builder.setClaims(claims);
        }

        if (ttl > 0) {
            builder.setExpiration(new Date(nowMillis + ttl));
        }
        return builder.compact();
    }

    public static Claims parseJWT(String jwtStr) {
        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwtStr)
                .getBody();
    }

}