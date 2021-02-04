package com.hitenine.blog;

import com.hitenine.blog.dao.SettingMapper;
import com.hitenine.blog.dao.UserMapper;
import com.hitenine.blog.utils.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApplicationTest {


    @Autowired
    private UserMapper userMapper;

    @Autowired
    private SettingMapper settingMapper;

    @Autowired
    RedisUtils redisUtils;

    @Test
    void contextLoads() {
        int i = userMapper.deleteUserByState("1355875146606440441");
        System.out.println(i);
    }

}
