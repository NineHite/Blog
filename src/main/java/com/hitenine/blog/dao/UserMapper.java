package com.hitenine.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.hitenine.blog.pojo.User;
import com.hitenine.blog.vo.UserVO;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * Mapper 接口
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

    @Select("select id, user_name, roles, avatar, email, sign, state, reg_ip, login_ip, create_time, update_time from tb_user")
    IPage<UserVO> selectUsers(IPage<UserVO> page);

    @Update("update `tb_user` set `password` = #{password} where `email` = #{email}")
    int updatePasswordByEmail(@Param("password") String password, @Param("email") String email);

    @Update("update `tb_user` set `email` = #{email} where `id` = #{id}")
    int updateEmailById(@Param("email") String email, @Param("id") String id);
}
