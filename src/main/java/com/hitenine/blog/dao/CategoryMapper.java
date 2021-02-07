package com.hitenine.blog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.hitenine.blog.pojo.Category;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryMapper extends BaseMapper<Category> {

    @Update("update `tb_categories` set `status` = '0' where id = #{categoryId}")
    int deleteCategoryByUpdateState(@Param("categoryId") String categoryId);
}
