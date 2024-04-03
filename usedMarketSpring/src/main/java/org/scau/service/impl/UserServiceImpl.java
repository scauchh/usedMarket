package org.scau.service.impl;

import org.scau.mapper.UserMapper;
import org.scau.pojo.Book;
import org.scau.pojo.PageBean;
import org.scau.pojo.User;
import org.scau.service.UserService;
import org.scau.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    // 修改用户权限
    @Override
    public void updateRoleID(String userName, Integer roleID) {
        userMapper.updateRoleID(userName, roleID);
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

    // 获取所有用户信息
    @Override
    public PageBean<User> getAllUserInfo(Integer pageNum, Integer pageSize, String userName, String userRole) {
        PageBean<User> pb = new PageBean<>();

        Map<String, Object> claim = ThreadLocalUtil.get();
        Integer userID = Integer.valueOf(claim.get("id").toString());

        // 查询分页结果
        int total = userMapper.getUserNum(userID);
        List<User> users = userMapper.getAllUserInfo(pageSize*(pageNum-1), pageSize, userID, userName, userRole);

        // 返回分页查询结果
        pb.setTotal(total);
        pb.setItems(users);
        return pb;
    }

    // 根据图片查询用户，用于判断图片是否有效
    @Override
    public User getUserByImage(String image) {
        return userMapper.getUserByImage(image);
    }
}
