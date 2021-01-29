package com.hitenine.blog.service.impl;

import com.hitenine.blog.pojo.DailyViewCount;
import com.hitenine.blog.dao.DailyViewCountMapper;
import com.hitenine.blog.service.DailyViewCountService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Hitenine
 * @since 2021-01-28
 */
@Service
public class DailyViewCountServiceImpl extends ServiceImpl<DailyViewCountMapper, DailyViewCount> implements DailyViewCountService {

}
