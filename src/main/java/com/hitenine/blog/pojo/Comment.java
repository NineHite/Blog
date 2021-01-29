package com.hitenine.blog.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Hitenine
 * @since 2021-01-28
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_comment")
public class Comment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 父内容
     */
    private String parentContent;

    /**
     * 文章ID
     */
    private String articleId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 评论用户的ID
     */
    private String userId;

    /**
     * 评论用户的头像
     */
    private String userAvatar;

    /**
     * 评论用户的名称
     */
    private String userName;

    /**
     * 状态（0表示删除，1表示正常）
     */
    private String state;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
