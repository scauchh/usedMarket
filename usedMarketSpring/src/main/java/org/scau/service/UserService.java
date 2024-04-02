package org.scau.service;

import org.scau.pojo.PageBean;
import org.scau.pojo.User;

import java.time.LocalDateTime;
import java.util.List;

public interface UserService {

    // 添加新用户
    void addUser(String userName, String password, LocalDateTime registerTime);

    // 更新用户数据
    void updateUser(User u);

    // 更新用户登录时间
    void updateLoginTime(Integer userID, LocalDateTime loginTime);

    // 通过ID查找用户
    User searchUserByID(Integer userID);

    // 通过用户名查找用户
    User searchUserByName(String userName);

    // 获取当前用户信息
    User getCurrentUser(Integer userID);

    // 获取所有用户信息
    PageBean<User> getAllUserInfo(Integer pageNum, Integer pageSize);

    // 根据图片查询用户，用于判断图片是否有效
    User getUserByImage(String image);
}
