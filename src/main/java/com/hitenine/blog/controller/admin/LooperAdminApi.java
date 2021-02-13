package com.hitenine.blog.controller.admin;


import com.hitenine.blog.pojo.Looper;
import com.hitenine.blog.response.ResponseResult;
import com.hitenine.blog.service.LooperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
public class LooperAdminApi {

    @Autowired
    private LooperService looperService;

    /**
     * 添加轮播图
     *
     * @param looper
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @PostMapping
    public ResponseResult addLooper(@RequestBody Looper looper) {
        return looperService.addLooper(looper);
    }

    /**
     * 删除轮播图
     *
     * @param looperId
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @DeleteMapping("/{looperId}")
    public ResponseResult deleteLooper(@PathVariable("looperId") String looperId) {
        return looperService.deleteLooper(looperId);
    }

    /**
     * 更新轮播图
     *
     * @param looperId
     * @param looper
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @PutMapping("/{looperId}")
    public ResponseResult updateLooper(@PathVariable("looperId") String looperId, @RequestBody Looper looper) {
        return looperService.updateLooper(looperId, looper);
    }

    /**
     * 获取轮播图
     *
     * @param looperId
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @GetMapping("/{looperId}")
    public ResponseResult getLooper(@PathVariable("looperId") String looperId) {
        return looperService.getLooper(looperId);
    }

    /**
     * 获取轮播图列表
     *
     * @param page
     * @param size
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @GetMapping("/list/{page}/{size}")
    public ResponseResult listLoopers(@PathVariable("page") int page, @PathVariable("size") int size) {
        return looperService.listLoopers(page, size);
    }
}

