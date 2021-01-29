package com.hitenine.blog.service.impl;

import com.hitenine.blog.pojo.Article;
import com.hitenine.blog.dao.ArticleMapper;
import com.hitenine.blog.service.ArticleService;
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
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

}
