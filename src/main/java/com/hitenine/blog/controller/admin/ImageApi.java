package com.hitenine.blog.controller.admin;


import com.hitenine.blog.pojo.Image;
import com.hitenine.blog.response.ResponseResult;
import org.springframework.web.bind.annotation.*;

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
public class ImageApi {

    /**
     * 添加图片
     *
     * @param image
     * @return
     */
    @PostMapping
    public ResponseResult uploadImage(@RequestBody Image image) {
        return null;
    }

    /**
     * 删除图片
     *
     * @param imageId
     * @return
     */
    @DeleteMapping("/{imageId}")
    public ResponseResult deleteImage(@PathVariable("imageId") String imageId) {
        return null;
    }

    /**
     * 更新图片
     *
     * @param imageId
     * @param image
     * @return
     */
    @PutMapping("/{imageId}")
    public ResponseResult updateImage(@PathVariable("imageId") String imageId, @RequestBody Image image) {
        return null;
    }

    /**
     * 获取图片
     *
     * @param imageId
     * @return
     */
    @GetMapping("/{imageId}")
    public ResponseResult getImage(@PathVariable("imageId") String imageId) {
        return null;
    }

    /**
     * 获取图片列表
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ResponseResult listImages(@RequestParam("page") int page, @RequestParam("size") int size) {
        return null;
    }
}

