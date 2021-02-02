package com.hitenine.blog.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author Hitenine
 * @since 2021-02-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("tb_refresh_token")
public class RefreshToken implements Serializable {

  private static final long serialVersionUID = 1L;

  @TableId
  private String id;
  private String refreshToken;
  private String userId;
  private String tokenKey;

}
