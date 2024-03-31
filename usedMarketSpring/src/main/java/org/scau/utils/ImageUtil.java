package org.scau.utils;

import org.scau.config.WebConfig;
import org.scau.pojo.Book;
import org.scau.pojo.Result;
import org.scau.pojo.User;
import org.scau.service.BookService;
import org.scau.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

@RestController
public class ImageUtil {

    final Logger logger = LoggerFactory.getLogger(ImageUtil.class);

    @Autowired
    BookService bookService;

    @Autowired
    UserService userService;

    // 根据当前时间生成文件名
    public static String genFileName(String originalFileName) {
        Date currentTime = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String timeStamp = dateFormat.format(currentTime);
        String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        return timeStamp + fileExtension;
    }

    // 上传图片
    @PostMapping("/uploadImg")
    public Result uploadImg(MultipartFile file) {
        if (file.isEmpty()) return Result.error("上传的文件为空");

        // 检查文件类型
        String contentType = file.getContentType();
        if (!MediaType.IMAGE_JPEG_VALUE.equals(contentType) && !MediaType.IMAGE_PNG_VALUE.equals(contentType)) {
            return Result.error("只能上传JPEG或PNG格式的图片");
        }

        // 检查文件大小
        long maxSizeBytes = 5 * 1024 * 1024;
        if (file.getSize() > maxSizeBytes) {
            return Result.error("图片大小不能超过5MB");
        }

        // 设置文件路径
        String fileName = genFileName(Objects.requireNonNull(file.getOriginalFilename()));

        String filePath = WebConfig.path;
        String realPath = "http://localhost:8080/pics/";

        File directory = new File(filePath);
        if (!directory.exists()) {
            if (!directory.mkdirs()) {
                logger.error("文件夹创建失败: " + filePath);
                return Result.error("文件夹创建失败");
            }
        }

        try {
            file.transferTo(new File(filePath + fileName));
            return Result.success(realPath + fileName);
        } catch (IOException e) {
            logger.error(e.toString());
            return Result.error("文件上传失败");
        }
    }

    // 清除无用图片
    @PostMapping("/removeImg")
    public Result removeImg() {
        File directory = new File(WebConfig.path);
        String url = "http://localhost:8080/pics/";

        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                User u = userService.getUserByImage(url + file.getName());
                Book b = bookService.getBookByImage(url + file.getName());
                if (u == null && b == null && !file.delete()) {
                    logger.error("文件删除失败: " + file.getAbsolutePath());
                }
            }
        }
        // 在用户退出登录时清除存储的信息
        ThreadLocalUtil.remove();
        return Result.success();
    }
}
