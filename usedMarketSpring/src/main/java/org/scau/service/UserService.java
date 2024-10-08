package org.scau.service;

import org.scau.model.PageBean;
import org.scau.model.pojo.User;

import java.time.LocalDateTime;

public interface UserService {

    // 添加新用户
    void addUser(String userName, String password, LocalDateTime registerTime);

    // 删除用户
    void deleteUser(Integer userID);

    // 更新用户数据
    void updateUser(User u);

    // 更新用户密码
    void updatePassword(Integer userID, String password);

    // 更新用户登录时间
    void updateLoginTime(Integer userID, LocalDateTime loginTime);

    // 修改用户权限
    void updateRole(String userName, Integer role);

    // 通过ID查找用户
    User searchUserByID(Integer userID);

    // 通过用户名查找用户
    User searchUserByName(String userName);

    // 获取所有用户信息
    PageBean<User> getAllUserInfo(Integer pageNum, Integer pageSize, Integer userID, String userName, String userRole);

    // 根据图片查询用户，用于判断图片是否有效
    Integer getUserByImage(String image);
}
