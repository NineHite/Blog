package com.hitenine.blog;

import com.hitenine.blog.dao.SettingMapper;
import com.hitenine.blog.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApplicationTest {

    @Autowired
    private SettingMapper settingMapper;

    @Autowired
    RedisUtils redisUtils;

    @Test
    void contextLoads() {
        redisUtils.set("haha", "world");
        String  haha = (String) redisUtils.get("haha");
        System.out.println(haha);
    }

}
