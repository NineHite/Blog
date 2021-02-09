package com.hitenine.blog;

import com.hitenine.blog.dao.CategoryMapper;
import com.hitenine.blog.dao.SettingMapper;
import com.hitenine.blog.dao.UserMapper;
import com.hitenine.blog.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

// @SpringBootTest
class BlogApplicationTest {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SettingMapper settingMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    RedisUtils redisUtils;

    @Test
    void contextLoads(){
        long currentTimeMillis = System.currentTimeMillis();
        CharSequence charSequence;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd");
        LocalDateTime currentDay = LocalDateTime.now();
        String current = currentDay.format(formatter);
        LocalDateTime localDateTime = LocalDateTime.now();
        Instant instant = localDateTime.atZone(ZoneId.systemDefault()).toInstant();
        long millis = instant.toEpochMilli();
        System.out.println("instant:"+millis);
        System.out.println(current);
    }

}
