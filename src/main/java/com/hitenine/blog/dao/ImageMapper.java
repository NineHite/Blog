package com.hitenine.blog.dao;

import com.hitenine.blog.pojo.Image;
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
public interface ImageMapper extends BaseMapper<Image> {

    @Update("update `tb_images` set `state` = '0' where id = #{imageId}")
    int deleteImageByUpdateState(@Param("imageId") String imageId);
}
