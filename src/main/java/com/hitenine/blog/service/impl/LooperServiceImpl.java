package com.hitenine.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hitenine.blog.dao.LooperMapper;
import com.hitenine.blog.pojo.Looper;
import com.hitenine.blog.response.ResponseResult;
import com.hitenine.blog.service.LooperService;
import com.hitenine.blog.utils.Constants;
import com.hitenine.blog.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Hitenine
 * @since 2021-01-28
 */
@Service
public class LooperServiceImpl extends ServiceImpl<LooperMapper, Looper> implements LooperService {

    @Autowired
    private LooperMapper looperMapper;

    @Autowired
    private IdWorker idWorker;

    @Override
    public ResponseResult addLooper(Looper looper) {
        // 检查数据
        String title = looper.getTitle();
        if (StringUtils.isEmpty(title)) {
            return ResponseResult.FAILED("标题不可以为空");
        }
        String imageUrl = looper.getImageUrl();
        if (StringUtils.isEmpty(imageUrl)) {
            return ResponseResult.FAILED("图片不可以为空");
        }
        String targetUrl = looper.getTargetUrl();
        if (StringUtils.isEmpty(targetUrl)) {
            return ResponseResult.FAILED("跳转链接不可以为空");
        }
        // 补充数据
        // looper.setId(idWorker.nextId() + "");
        // looper.setCreateTime(LocalDateTime.now());
        // looper.setUpdateTime(LocalDateTime.now());

        // 保存数据
        looperMapper.insert(looper);
        // 返回结果
        return ResponseResult.SUCCESS("轮播图添加成功");
    }

    @Override
    public ResponseResult getLooper(String looperId) {
        Looper looper = looperMapper.selectById(looperId);
        if (looper == null) {
            return ResponseResult.FAILED("轮播图不存在");
        }
        return ResponseResult.SUCCESS("轮播图获取成功").setData(looper);
    }

    @Override
    public ResponseResult listLoopers(int page, int size) {
        page = Math.max(page, Constants.PageSize.DEFAULT_PAGE);
        size = Math.max(size, Constants.PageSize.MIN_SIZE);
        Page<Looper> looperPage = new Page<>();
        looperPage.addOrder(OrderItem.desc("create_time"));
        Page<Looper> all = looperMapper.selectPage(looperPage, null);
        return ResponseResult.SUCCESS("获取轮播图列表成功").setData(all);
    }

    @Override
    public ResponseResult updateLooper(String looperId, Looper looper) {
        // 找出来
        Looper looperFromDb = looperMapper.selectById(looperId);
        if (looperFromDb == null) {
            return ResponseResult.FAILED("轮播图不存在");
        }
        // 不可以为空，要判空
        String title = looper.getTitle();
        if (!StringUtils.isEmpty(title)) {
            looperFromDb.setTitle(title);
        }
        String imageUrl = looper.getImageUrl();
        if (!StringUtils.isEmpty(imageUrl)) {
            looperFromDb.setImageUrl(imageUrl);
        }
        String targetUrl = looper.getTargetUrl();
        if (!StringUtils.isEmpty(targetUrl)) {
            looperFromDb.setTargetUrl(targetUrl);
        }
        if (!StringUtils.isEmpty(looper.getState())) {
            looperFromDb.setState(looper.getState());
        }
        looperFromDb.setOrder(looper.getOrder());
        // looperFromDb.setUpdateTime(LocalDateTime.now());
        int result = looperMapper.updateById(looperFromDb);
        return result > 0 ? ResponseResult.SUCCESS("轮播图更新成功") : ResponseResult.FAILED("轮播图更新失败");
    }

    @Override
    public ResponseResult deleteLooper(String looperId) {
        int result = looperMapper.deleteById(looperId);
        return result > 0 ? ResponseResult.SUCCESS("轮播图删除成功") : ResponseResult.FAILED("轮播图不存在");

    }
}
