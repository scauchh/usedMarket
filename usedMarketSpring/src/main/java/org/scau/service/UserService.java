package org.scau.service;

import org.scau.pojo.User;

public interface UserService {

    // 添加新用户
    void addUser(String userName, String password);

    // 更新用户数据
    void updateUser(User u);

    // 通过ID查找用户
    User searchUserByID(Integer userID);

    // 通过用户名查找用户
    User searchUserByName(String userName);

    // 获取当前用户信息
    User getCurrentUser(Integer userID);

    // 根据图片查询用户，用于判断图片是否有效
    User getUserByImage(String image);
}
