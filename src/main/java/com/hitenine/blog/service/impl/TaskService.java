package com.hitenine.blog.service.impl;

import com.hitenine.blog.utils.EmailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * 异步
 *
 * @author Hitenine
 * @date 2021/1/30 22:39
 * @version 1.0
 */
@Service
public class TaskService {

    /**
     * 异步发送邮件
     *
     * @param verifyCode
     * @param address
     * @throws Exception
     */
    @Async
    public void sendEmailVerifyCode(String verifyCode, String address) throws Exception {
        EmailSender.sendRegisterVerifyCode(verifyCode, address);
    }
}
