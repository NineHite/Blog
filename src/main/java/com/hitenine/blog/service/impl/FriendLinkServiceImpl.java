package com.hitenine.blog.service.impl;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hitenine.blog.dao.FriendLinkMapper;
import com.hitenine.blog.pojo.FriendLink;
import com.hitenine.blog.response.ResponseResult;
import com.hitenine.blog.service.FriendLinkService;
import com.hitenine.blog.utils.Constants;
import com.hitenine.blog.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Hitenine
 * @since 2021-01-28
 */
@Service
@Transactional
public class FriendLinkServiceImpl extends ServiceImpl<FriendLinkMapper, FriendLink> implements FriendLinkService {

    @Autowired
    private FriendLinkMapper friendLinkMapper;

    @Autowired
    private IdWorker idWorker;

    @Override
    public ResponseResult addFriendLink(FriendLink friendLink) {
        // 判断数据
        String name = friendLink.getName();
        if (StringUtils.isEmpty(name)) {
            return ResponseResult.FAILED("对方网站名不可以为空");
        }
        String url = friendLink.getUrl();
        if (StringUtils.isEmpty(url)) {
            return ResponseResult.FAILED("链接url不可以为空");
        }
        String logo = friendLink.getLogo();
        if (StringUtils.isEmpty(logo)) {
            return ResponseResult.FAILED("logo不可以为空");
        }
        // 补全数据
        // friendLink.setId(idWorker.nextId() + "");
        // friendLink.setCreateTime(LocalDateTime.now());
        // friendLink.setUpdateTime(LocalDateTime.now());

        // 保存数据
        friendLinkMapper.insert(friendLink);
        // 返回结果
        return ResponseResult.SUCCESS("友情链接添加成功");
    }

    @Override
    public ResponseResult getFriendLink(String friendLinkId) {
        FriendLink friendLink = friendLinkMapper.selectById(friendLinkId);
        if (friendLink == null) {
            return ResponseResult.FAILED("友情链接不存在");
        }
        return ResponseResult.SUCCESS("获取友情链接成功").setData(friendLink);
    }

    @Override
    public ResponseResult listFriendLinks(int page, int size) {
        page = Math.max(page, Constants.PageSize.DEFAULT_PAGE);
        size = Math.max(size, Constants.PageSize.MIN_SIZE);
        Page<FriendLink> friendLinkPage = new Page<>(page, size);
        friendLinkPage.addOrder(OrderItem.desc("create_time"));
        Page<FriendLink> all = friendLinkMapper.selectPage(friendLinkPage, null);
        return ResponseResult.SUCCESS("获取友情链接列表成功").setData(all);
    }

    @Override
    public ResponseResult deleteFriendLink(String friendLinkId) {
        int result = friendLinkMapper.deleteById(friendLinkId);
        return result > 0 ? ResponseResult.SUCCESS("友情链接删除成功") : ResponseResult.FAILED("友情链接不存在");
    }

    /**
     * 更新内容：
     *         logo
     *         对方网站名
     *         url
     *         order
     *
     * @param friendLinkId
     * @param friendLink
     * @return
     */
    @Override
    public ResponseResult updateFriendLink(String friendLinkId, FriendLink friendLink) {
        FriendLink friendLinkFromDb = friendLinkMapper.selectById(friendLinkId);
        if (friendLinkFromDb == null) {
            return ResponseResult.FAILED("友情链接更新失败");
        }
        String name = friendLink.getName();
        if (!StringUtils.isEmpty(name)) {
            friendLinkFromDb.setName(name);
        }
        String logo = friendLink.getLogo();
        if (!StringUtils.isEmpty(logo)) {
            friendLinkFromDb.setLogo(logo);
        }
        String url = friendLink.getUrl();
        if (!StringUtils.isEmpty(url)) {
            friendLinkFromDb.setUrl(url);
        }
        friendLinkFromDb.setOrder(friendLink.getOrder());
        // friendLinkFromDb.setUpdateTime(LocalDateTime.now());
        friendLinkMapper.updateById(friendLink);
        return ResponseResult.SUCCESS("友情链接修改成功");
    }
}
