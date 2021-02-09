package com.hitenine.blog.controller.admin;


import com.hitenine.blog.pojo.FriendLink;
import com.hitenine.blog.response.ResponseResult;
import com.hitenine.blog.service.FriendLinkService;
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
@RequestMapping("/admin/friend_link")
public class FriendLinkAdminApi {

    @Autowired
    private FriendLinkService friendLinkService;

    /**
     * 添加友链
     *
     * @param friendLink
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @PostMapping
    public ResponseResult addFriendLink(@RequestBody FriendLink friendLink) {
        return friendLinkService.addFriendLink(friendLink);
    }

    /**
     * 删除友链
     *
     * @param friendLinkId
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @DeleteMapping("{friendLinkId}")
    public ResponseResult deleteFriendLink(@PathVariable("friendLinkId") String friendLinkId) {
        return friendLinkService.deleteFriendLink(friendLinkId);
    }

    /**
     * 更新友链
     *
     * @param friendLinkId
     * @param friendLink
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @PutMapping("/{friendLinkId}")
    public ResponseResult updateFriendLink(@PathVariable("friendLinkId") String friendLinkId, @RequestBody FriendLink friendLink) {
        return friendLinkService.updateFriendLink(friendLinkId, friendLink);
    }

    /**
     * 获取友链
     *
     * @param friendLinkId
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @GetMapping("/{friendLinkId}")
    public ResponseResult getFriendLink(@PathVariable("friendLinkId") String friendLinkId) {
        return friendLinkService.getFriendLink(friendLinkId);
    }

    /**
     * 获取友链列表
     *
     * @param page
     * @param size
     * @return
     */
    @PreAuthorize("@permission.admin()")
    @GetMapping("/list/{page}/{size}")
    public ResponseResult listFriendLinks(@RequestParam("page") int page, @RequestParam("size") int size) {
        return friendLinkService.listFriendLinks(page, size);
    }
}

