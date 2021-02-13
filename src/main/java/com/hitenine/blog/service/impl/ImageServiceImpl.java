package com.hitenine.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hitenine.blog.dao.ImageMapper;
import com.hitenine.blog.pojo.Image;
import com.hitenine.blog.response.ResponseResult;
import com.hitenine.blog.service.ImageService;
import com.hitenine.blog.service.UserService;
import com.hitenine.blog.utils.Constants;
import com.hitenine.blog.utils.IdWorker;
import com.hitenine.blog.utils.LocalDateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * 保存文件 : 多人上传文件可能有一样的图片，可以在数据库设计一个md5值，保存之前计算一下图片的md5值，
 * 根据md5值去数据库查找，如果有直接指向它，没有再保存，很不错的思路！
 *
 * @author Hitenine
 * @since 2021-01-28
 */
@Slf4j
@Service
@Transactional
public class ImageServiceImpl extends ServiceImpl<ImageMapper, Image> implements ImageService {

    @Autowired
    private ImageMapper imageMapper;

    @Autowired
    private UserService userService;

    @Value("${sob.blog.image.save-path}")
    public String imagePath;

    @Value("${sob.blog.image.max-size}")
    public long maxSize;

    @Autowired
    private IdWorker idWorker;

    public static String formatter = "yyyy_MM_dd";

    /**
     * 上传的路径：可以配置，在配置文件里配置
     * 上传的内容，命名-->可以用ID，每天一个文件夹保存
     * 限制文件的大小，通过配置文件配置
     * 保存记录到数据库里
     * ID / 存储路径 / url / 原名称 / 状态 / 用户ID / 创建日期 / 更新日期
     *
     * @param file
     * @return
     */
    @Override
    public ResponseResult uploadImage(MultipartFile file) {
        // 判断是否有文件
        if (file == null) {
            return ResponseResult.FAILED("文件不可以为空");
        }
        // 判断文件类型，我们只支持图片上传，比如：png,jpg,gif
        String contentType = file.getContentType();
        if (StringUtils.isEmpty(contentType)) {
            return ResponseResult.FAILED("文件格式错误");
        }
        log.info("contentType -- > " + contentType);
        // 获取相关数据，比如说文件类型，文件名称
        String originalFilename = file.getOriginalFilename();
        log.info("originalFilename -- > " + originalFilename);
        String type = null;
        type = getType(contentType, originalFilename);
        if (StringUtils.isEmpty(type)) {
            return ResponseResult.FAILED("不支持该文件类型");
        }

        long size = file.getSize();
        log.info("size -- > " + size);
        if (size > maxSize) {
            return ResponseResult.FAILED("图片最大支持" + (maxSize / 1024 / 1024) + "MB");
        }
        // 创建文件的保存目录
        // 规则：配置目录/日期/类型/ID.类型
        // LocalDateTime localDateTime = LocalDateTime.now();
        // long currentTimeMillis = localDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        // String currentDay = localDateTime.format(formatter);
        long currentTimeMillis = System.currentTimeMillis();
        String currentDay = LocalDateTimeUtils.timeStamp2Date(currentTimeMillis, formatter);
        log.info("currentDay -- > " + currentDay);
        String dayPath = imagePath + File.separator + currentDay;
        File dayPathFile = new File(dayPath);
        // 判断日期文件夹是否存在
        if (!dayPathFile.exists()) {
            dayPathFile.mkdirs();
        }
        String targetNameId = String.valueOf(idWorker.nextId());
        String targetPath = dayPath +
                File.separator + type + File.separator +
                targetNameId + "." + type;
        File targetFile = new File(targetPath);
        // 判断类型文件夹是否存在
        if (!targetFile.getParentFile().exists()) {
            targetFile.mkdirs();
        }
        log.info("targetFile -- > " + targetFile);
        // 保存文件 : 多人上传文件可能有一样的图片，可以在数据库设计一个md5值，保存之前计算一下图片的md5值，
        // 根据md5值去数据库查找，如果有直接指向它，没有再保存，很不错的思路！
        try {
            if (!targetFile.exists()) {
                targetFile.createNewFile();
            }
            file.transferTo(targetFile);
            // 返回结果：包含这个图片的名称和访问路径
            // 第一个是访问路径 -- > 对应着解析来
            Map<String, String> result = new HashMap<>();
            String resultPath = currentTimeMillis + "_" + targetNameId + "." + type;
            result.put("path", resultPath);
            result.put("name", originalFilename);
            // 第二个是名称 -- > alt="图片描述",如果不写，前端可以使用名称作为这个描述
            // 记录文件
            // 保存数据
            Image image = new Image();
            image.setContentType(contentType);
            image.setId(targetNameId);
            // image.setCreateTime(LocalDateTime.now());
            // image.setUpdateTime(LocalDateTime.now());
            image.setPath(targetFile.getPath());
            image.setName(originalFilename);
            image.setUrl(resultPath);
            // image.setState("1");
            image.setUserId(userService.checkUser().getId());
            imageMapper.insert(image);
            // 返回结果
            return ResponseResult.SUCCESS("文件上传成功").setData(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ResponseResult.FAILED("图片上传失败，请稍后重试");
    }

    private String getType(String contentType, String name) {
        String type = null;
        if (Constants.ImageType.TYPE_PNG_WITH_PREFIX.equals(contentType)
                && name.endsWith(Constants.ImageType.TYPE_PNG)) {
            type = Constants.ImageType.TYPE_PNG;
        } else if (Constants.ImageType.TYPE_JPG_WITH_PREFIX.equals(contentType)
                && name.endsWith(Constants.ImageType.TYPE_JPG)) {
            type = Constants.ImageType.TYPE_JPG;
        } else if (Constants.ImageType.TYPE_GIF_WITH_PREFIX.equals(contentType)
                && name.endsWith(Constants.ImageType.TYPE_GIF)) {
            type = Constants.ImageType.TYPE_GIF;
        }
        return type;
    }

    private String getContentType(String name) {
        String type = null;
        if (name.endsWith(Constants.ImageType.TYPE_PNG)) {
            type = Constants.ImageType.TYPE_PNG_WITH_PREFIX;
        } else if (name.endsWith(Constants.ImageType.TYPE_JPG)) {
            type = Constants.ImageType.TYPE_JPG_WITH_PREFIX;
        } else if (name.endsWith(Constants.ImageType.TYPE_GIF)) {
            type = Constants.ImageType.TYPE_GIF_WITH_PREFIX;
        }
        return type;
    }

    @Override
    public void getImage(HttpServletResponse response, String imageId) throws IOException {
        // TODO:
        // 根据尺寸来动态返回前端
        // 好处：减少带宽占用，传输速度快
        // 缺点：消耗后台CPU资源
        // 推荐做法：上传来的时候，把图片复制成三个尺寸：大，中，小
        // 根据尺寸范围，返回结果

        // 配置的目录已知
        // 需要日期
        String[] paths = imageId.split("_");
        long dayValue = Long.parseLong(paths[0]);
        String format = LocalDateTimeUtils.timeStamp2Date(dayValue, formatter);
        log.info("format -- > " + format);
        // ID
        String name = paths[1];
        // 需要类型
        // 使用日期的时间戳_ID.类型
        String type = name.substring(name.length() - 3);
        String targetPath = imagePath + File.separator + format + File.separator + type + File.separator + name;
        log.info("targetPath -- > " + targetPath);
        File file = new File(targetPath);
        OutputStream writer = null;
        FileInputStream fis = null;
        try {
            String contentType = getContentType(name);
            response.setContentType(contentType);
            writer = response.getOutputStream();
            // 读取
            fis = new FileInputStream(file);
            byte[] buff = new byte[1024];
            int len;
            while ((len = fis.read(buff)) != -1) {
                writer.write(buff, 0, len);
            }
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
            if (writer != null) {
                writer.close();
            }
        }
    }

    @Override
    public ResponseResult listImages(int page, int size) {
        page = Math.max(page, Constants.PageSize.DEFAULT_PAGE);
        size = Math.max(size, Constants.PageSize.MIN_SIZE);
        Page<Image> imagePage = new Page<>(page, size);
        imagePage.addOrder(OrderItem.desc("create_time"));
        Page<Image> all = imageMapper.selectPage(imagePage,
                new QueryWrapper<Image>().lambda()
                        .eq(Image::getState, "1")
                        .eq(Image::getUserId, userService.checkUser().getId()));
        return ResponseResult.SUCCESS("获取图片列表成功").setData(all);
    }

    @Override
    public ResponseResult deleteImageById(String imageId) {
        int result = imageMapper.deleteImageByUpdateState(imageId);
        return result > 0 ? ResponseResult.SUCCESS("图片删除成功") : ResponseResult.FAILED("图片不存在");
    }
}
