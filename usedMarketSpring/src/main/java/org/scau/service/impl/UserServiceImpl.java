package org.scau.service.impl;

import org.scau.mapper.UserMapper;
import org.scau.pojo.User;
import org.scau.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    // 添加新用户
    @Override
    public void addUser(String userName, String password, LocalDateTime registerTime) {
        userMapper.addUser(userName, password, registerTime);
    }

    // 更新用户信息
    @Override
    public void updateUser(User u) {
        userMapper.updateUser(u);
    }

    // 更新用户登录时间
    @Override
    public void updateLoginTime(Integer userID, LocalDateTime loginTime) {
        userMapper.updateLoginTime(userID, loginTime);
    }

    // 根据ID查找用户
    @Override
    public User searchUserByID(Integer userID) {
        return userMapper.selectUserByID(userID);
    }

    // 根据用户名查找用户
    @Override
    public User searchUserByName(String userName) {
        return userMapper.selectUserByName(userName);
    }

    // 获取当前用户信息
    @Override
    public User getCurrentUser(Integer userID) {
        return userMapper.getCurrentUser(userID);
    }

    // 根据图片查询用户，用于判断图片是否有效
    @Override
    public User getUserByImage(String image) {
        return userMapper.getUserByImage(image);
    }
}
