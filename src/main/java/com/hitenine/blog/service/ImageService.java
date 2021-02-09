package com.hitenine.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hitenine.blog.pojo.Image;
import com.hitenine.blog.response.ResponseResult;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Hitenine
 * @since 2021-01-28
 */
public interface ImageService extends IService<Image> {

    ResponseResult uploadImage(MultipartFile file);

    void getImage(HttpServletResponse response, String imageId) throws IOException;

    ResponseResult listImages(int page, int size);

    ResponseResult deleteImageById(String imageId);
}
