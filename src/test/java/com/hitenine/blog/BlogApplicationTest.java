package com.hitenine.blog;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hitenine.blog.dao.CategoryMapper;
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
    private CategoryMapper categoryMapper;

    @Autowired
    RedisUtils redisUtils;

    @Test
    void contextLoads() throws JsonProcessingException {
    }

}
