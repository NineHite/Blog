package com.hitenine.blog;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hitenine.blog.dao.SettingMapper;
import com.hitenine.blog.dao.UserMapper;
import com.hitenine.blog.utils.RedisUtils;
import com.hitenine.blog.vo.UserVO;
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
    void contextLoads() throws JsonProcessingException {
        // Page<User> userPage = userMapper.selectPage(new Page<>(1, 10), new QueryWrapper<User>().orderByAsc("create_time"));
        IPage<UserVO> userIPage = userMapper.selectUsers(new Page<>(1, 5));
        ObjectMapper objectMapper = new ObjectMapper();
        // objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // objectMapper.setDateFormat(sdf);
        String s = objectMapper.writeValueAsString(userIPage);
        System.out.println(s);
    }

}
