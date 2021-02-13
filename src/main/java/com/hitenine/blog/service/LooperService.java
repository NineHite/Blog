package com.hitenine.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hitenine.blog.pojo.Looper;
import com.hitenine.blog.response.ResponseResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Hitenine
 * @since 2021-01-28
 */
public interface LooperService extends IService<Looper> {

    ResponseResult addLooper(Looper looper);

    ResponseResult getLooper(String looperId);

    ResponseResult listLoopers(int page, int size);

    ResponseResult updateLooper(String looperId, Looper looper);

    ResponseResult deleteLooper(String looperId);
}
