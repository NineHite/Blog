package com.hitenine.blog.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class JwtUtilsTest {

    @Test
    void testCreateToken() {
        Map<String, Object> claims = new HashMap<>();
        claims.put("id", "1355138071204573186");
        claims.put("userName", "阳光沙滩");
        claims.put("roles", "阳光沙滩");
        claims.put("avatar", "https://cdn.sunofbeaches.com/images/default_avatar.png");
        claims.put("email", "1751050890@qq.com");
        claims.put("state", "1");
        // String md5DigestAsHex = DigestUtils.md5DigestAsHex("123456".getBytes());
        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "123456")
                .setClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + 30 * 1000));
        System.out.println(builder.compact());

    }

    @Test
    void testParseToken() {
        Claims body = Jwts.parser()
                .setSigningKey("123456")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJyb2xlcyI6IumYs-WFieaymea7qSIsImlkIjoiMTM1NTEzODA3MTIwNDU3MzE4NiIsImF2YXRhciI6Imh0dHBzOi8vY2RuLnN1bm9mYmVhY2hlcy5jb20vaW1hZ2VzL2RlZmF1bHRfYXZhdGFyLnBuZyIsInN0YXRlIjoiMSIsInVzZXJOYW1lIjoi6Ziz5YWJ5rKZ5rupIiwiZXhwIjoxNjEyMTgzNzk5LCJlbWFpbCI6IjE3NTEwNTA4OTBAcXEuY29tIn0.i0LXlF2sNwIHMBFiHsyaYsKJQLR_Dh3b6CsdGawE7fg")
                .getBody();
        System.out.println(body);
    }
}