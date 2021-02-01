package com.hitenine.blog.utils;

/**
  *
 * @author Hitenine
 */
public interface Constants {

    interface User {
        String ROLE_ADMIN = "role_admin";
        String ROLE_NORMAL = "role_normal";
        String DEFAULT_AVATAR = "https://cdn.sunofbeaches.com/images/default_avatar.png";
        String DEFAULT_STATE = "1";
        String KEY_CAPTCHA_CONTENT = "key_captcha_content_";
        String KEY_EMAIL_CONTENT = "key_email_content_";
        String KEY_EMAIL_SEND_IP = "key_email_send_ip_";
        String KEY_EMAIL_SEND_ADDRESS = "key_email_send_address_";
    }

    interface Setting {
        String MANAGER_ACCOUNT_INIT_STATE = "manager_account_init_state";
    }
}
