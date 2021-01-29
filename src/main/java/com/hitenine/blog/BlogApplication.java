package com.hitenine.blog;

import com.hitenine.blog.utils.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

    /**
     * <p>
     *     雪花算法
     * </p>
     *
     * @return
     */
    @Bean
    public IdWorker createIdWorker() {
        return new IdWorker(0, 0);
    }

    /**
     * <p>
     *     SpringSecurity加密
     * </p>
     *
     * @return
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
