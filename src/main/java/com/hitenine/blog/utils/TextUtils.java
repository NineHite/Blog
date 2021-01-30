package com.hitenine.blog.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Hitenine
 * @version 1.0
 * @date 2021/1/29 16:05
 */
public class TextUtils {

    /**
     * 邮箱规则
     */
    public static final String EMAIL_PATTERN = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";

    public static boolean isEmpty(String text) {
        return text == null || text.length() == 0;
    }

    public static boolean isEmailAddressOk(String emailAddress) {
        Pattern p = Pattern.compile(EMAIL_PATTERN);
        Matcher m = p.matcher(emailAddress);
        return m.matches();
    }
}
