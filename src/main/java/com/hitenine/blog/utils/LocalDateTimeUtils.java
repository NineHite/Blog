package com.hitenine.blog.utils;

import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * @author Hitenine
 * @version 1.0
 * @date 2021/2/9 17:48
 */
public class LocalDateTimeUtils {
    /**
     * 时间戳转日期/时间
     *
     * @param seconds 时间戳
     * @param pattern 时间格式
     * @return 格式化的时间
     */
    public static String timeStamp2Date(long seconds, String pattern) {
        String time = "暂无数据";
        if (StringUtils.isEmpty(pattern)) {
            pattern = "yyyy-MM-dd  HH:mm:ss";
        }
        LocalDateTime dateTime = LocalDateTime.ofEpochSecond(seconds / 1000L, 0, ZoneOffset.ofHours(8));
        if (seconds != 0) {
            time = dateTime.format(DateTimeFormatter.ofPattern(pattern));
        }
        return time;
    }

    /**
     * 日期/时间转时间戳
     *
     * @param date    时间
     * @param pattern 时间格式
     * @return 时间戳
     */
    public static long date2TimeStamp(String date, String pattern) {
        long timeStamp;
        if (StringUtils.isEmpty(pattern)) {
            pattern = "yyyy-MM-dd  HH:mm:ss";
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
        timeStamp = localDateTime.toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
        return timeStamp;
    }
}
