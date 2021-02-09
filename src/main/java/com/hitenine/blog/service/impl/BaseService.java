package com.hitenine.blog.service.impl;

import com.hitenine.blog.utils.Constants;

/**
 * @author Hitenine
 * @version 1.0
 * @date 2021/2/9 20:38
 */
public class BaseService {
    int checkPage(int page) {
        return Math.max(page, Constants.PageSize.DEFAULT_PAGE);
    }

    int checkSize(int size) {
        return Math.max(size, Constants.PageSize.MIN_SIZE);
    }
}
