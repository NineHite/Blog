package com.hitenine.blog.config;

import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Hitenine
 * @version 1.0
 * @date 2021/1/28 17:46
 */
@Configuration
@MapperScan("com.hitenine.blog.dao")
public class MyBatisPlusConfiguration {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        return new PaginationInterceptor();
    }
}
