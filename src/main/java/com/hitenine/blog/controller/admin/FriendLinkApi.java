package com.hitenine.blog.controller.admin;


import com.hitenine.blog.pojo.FriendLink;
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
@RequestMapping("/admin/friend_link")
public class FriendLinkApi {

    /**
     * 添加友链
     *
     * @param friendLink
     * @return
     */
    @PostMapping
    public ResponseResult addFriend(@RequestBody FriendLink friendLink) {
        return null;
    }

    /**
     * 删除友链
     *
     * @param friendLinkId
     * @return
     */
    @DeleteMapping("friendLinkId")
    public ResponseResult deleteFriend(@PathVariable("friendLinkId") String friendLinkId) {
        return null;
    }

    /**
     * 更新友链
     *
     * @param friendLinkId
     * @param friendLink
     * @return
     */
    @PutMapping("/{friendLinkId}")
    public ResponseResult updateFriend(@PathVariable("friendLinkId") String friendLinkId, @RequestBody FriendLink friendLink) {
        return null;
    }

    /**
     * 获取友链
     *
     * @param friendLinkId
     * @return
     */
    @GetMapping("/{friendLinkId}")
    public ResponseResult getFriend(@PathVariable("friendLinkId") String friendLinkId) {
        return null;
    }

    /**
     * 获取友链列表
     *
     * @param page
     * @param size
     * @return
     */
    @GetMapping("/list")
    public ResponseResult listFriendLinks(@RequestParam("page") int page, @RequestParam("size") int size) {
        return null;
    }
}

