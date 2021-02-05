package com.hitenine.blog.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Hitenine
 * @since 2021-02-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class UserVO implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String userName;
    private String roles;
    private String avatar;
    private String email;
    private String sign;
    private String state;
    private String regIp;
    private String loginIp;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

}
