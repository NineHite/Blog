package com.hitenine.blog.controller.admin;


import com.hitenine.blog.response.ResponseResult;
import com.hitenine.blog.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author Hitenine
 * @since 2021-01-28
 */
@RestController
@RequestMapping("/admin/image")
public class ImageAdminApi {

    @Autowired
    private ImageService imageService;

    /**
     * 关于图片（文件）上传
     * 一般来说，现在比较常用的是对象存储-->很简单，看文档
     * 使用 nginx + fashDFS ==> fastDFS-->处理上传文件，nginx--> 负责处理文件访问
     *
     * @param file
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @PostMapping
    public ResponseResult uploadImage(@RequestParam("file") MultipartFile file) {
        return imageService.uploadImage(file);
    }

    /**
     * 删除图片
     *
     * @param imageId
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @DeleteMapping("/{imageId}")
    public ResponseResult deleteImage(@PathVariable("imageId") String imageId) {
        return imageService.deleteImageById(imageId);
    }


    /**
     * 获取图片
     *
     * @param imageId
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @GetMapping("/{imageId}")
    public void getImage(HttpServletResponse response, @PathVariable("imageId") String imageId) {
        try {
            imageService.getImage(response, imageId);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取图片列表
     *
     * @param page
     * @param size
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @GetMapping("/list/{page}/{size}")
    public ResponseResult listImages(@PathVariable("page") int page, @PathVariable("size") int size) {
        return imageService.listImages(page, size);
    }
}

