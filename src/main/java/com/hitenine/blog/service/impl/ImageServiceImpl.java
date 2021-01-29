package com.hitenine.blog.service.impl;

import com.hitenine.blog.pojo.Image;
import com.hitenine.blog.dao.ImageMapper;
import com.hitenine.blog.service.ImageService;
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
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements ImageService {

}
