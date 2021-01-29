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
@TableName("tb_looper")
public class Looper implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 轮播图标题
     */
    private String title;

    /**
     * 顺序
     */
    private Integer order;

    /**
     * 状态：0表示不可用，1表示正常
     */
    private String state;

    /**
     * 目标URL
     */
    private String targetUrl;

    /**
     * 图片地址
     */
    private String imageUrl;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updateTime;

}
