package com.hitenine.blog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hitenine.blog.pojo.FriendLink;
import com.hitenine.blog.response.ResponseResult;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Hitenine
 * @since 2021-01-28
 */
public interface FriendLinkService extends IService<FriendLink> {

    ResponseResult addFriendLink(FriendLink friendLink);

    ResponseResult getFriendLink(String friendLinkId);

    ResponseResult listFriendLinks(int page, int size);

    ResponseResult deleteFriendLink(String friendLinkId);

    ResponseResult updateFriendLink(String friendLinkId, FriendLink friendLink);
}
