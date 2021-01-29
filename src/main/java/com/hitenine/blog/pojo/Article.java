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
@TableName("tb_article")
public class Article implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 标题
     */
    private String title;

    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 分类ID
     */
    private String categoryId;

    /**
     * 文章内容
     */
    private String content;

    /**
     * 类型（0表示富文本，1表示markdown）
     */
    private String type;

    /**
     * 状态（0表示已发布，1表示草稿，2表示删除）
     */
    private String state;

    /**
     * 摘要
     */
    private String summary;

    /**
     * 标签
     */
    private String labels;

    /**
     * 阅读数量
     */
    private Integer viewCount;

    /**
     * 发布时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
