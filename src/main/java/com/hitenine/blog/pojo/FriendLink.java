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
@TableName("tb_friends")
public class FriendLink implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 友情链接名称
     */
    private String name;

    /**
     * 友情链接logo
     */
    private String logo;

    /**
     * 友情链接
     */
    private String url;

    /**
     * 顺序
     */
    private Integer order;

    /**
     * 友情链接状态:0表示不可用，1表示正常
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
