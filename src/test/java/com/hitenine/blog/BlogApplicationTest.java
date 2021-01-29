package com.hitenine.blog;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.hitenine.blog.dao.SettingMapper;
import com.hitenine.blog.pojo.Setting;
import com.hitenine.blog.utils.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BlogApplicationTest {

    @Autowired
    private SettingMapper settingMapper;

    @Test
    void contextLoads() {
        Setting managerAccountState = settingMapper.selectOne(new QueryWrapper<Setting>().eq("`key`", Constants.Setting.MANAGER_ACCOUNT_INIT_STATE));
        System.out.println(managerAccountState);

    }

}
