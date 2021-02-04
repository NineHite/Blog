package com.hitenine.blog.dao;

import com.hitenine.blog.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Hitenine
 * @since 2021-01-28
 */
@Repository
public interface UserMapper extends BaseMapper<User> {

    /**
     * 通过修改用户的状态来删除用户
     *
     * @param userId
     * @return
     */
    @Update("update `tb_user` set state = '0' where id = #{userId}")
    int deleteUserByState(@Param("userId") String userId);

}
