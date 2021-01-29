package com.hitenine.blog.service.impl;

import com.hitenine.blog.pojo.Comment;
import com.hitenine.blog.dao.CommentMapper;
import com.hitenine.blog.service.CommentService;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
