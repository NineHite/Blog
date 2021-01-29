package com.hitenine.blog.controller.admin;


import com.hitenine.blog.pojo.Looper;
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
@RequestMapping("/admin/looper")
public class LooperApi {

    /**
     * 添加轮播图
     *
     * @param looper
     * @return
     */
    @PostMapping
    public ResponseResult uploadLooper(@RequestBody Looper looper) {
        return null;
    }

    /**
     * 删除轮播图
     *
     * @param looperId
     * @return
     */
    @DeleteMapping("/{looperId}")
    public ResponseResult deleteLooper(@PathVariable("looperId") String looperId) {
        return null;
    }

    /**
     * 更新轮播图
     *
     * @param looperId
     * @param looper
     * @return
     */
    @PutMapping("/{looperId}")
    public ResponseResult updateLooper(@PathVariable("looperId") String looperId, @RequestBody Looper looper) {
        return null;
    }

    /**
     * 获取轮播图
     *
     * @param looperId
     * @return
     */
    @GetMapping("/{looperId}")
    public ResponseResult getLooper(@PathVariable("looperId") String looperId) {
        return null;
    }

    /**
     * 获取轮播图列表
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ResponseResult listLoopers(@RequestParam("page") int page, @RequestParam("size") int size) {
        return null;
    }
}

