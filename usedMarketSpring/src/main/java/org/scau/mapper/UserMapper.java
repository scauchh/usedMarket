package org.scau.mapper;

import org.apache.ibatis.annotations.*;
import org.scau.pojo.User;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface UserMapper {

    // 添加新用户
    @Insert("INSERT INTO user (user_name, nick_name, password, register_time, login_time) " +
            "VALUES (#{userName}, #{userName}, #{password}, #{registerTime}, #{registerTime})")
    void addUser(String userName, String password, LocalDateTime registerTime);

    // 删除用户
    @Delete("DELETE FROM user WHERE user_id = #{userID}")
    void deleteUser(Integer userID);

    // 更新用户信息
    void updateUser(User u);

    // 更新用户密码
    @Update("UPDATE user SET password = #{password} WHERE user_id = #{userID}")
    void updatePassword(Integer userID, String password);

    // 更新用户登录时间
    @Update("UPDATE user SET login_time = #{loginTime} WHERE user_id = #{userID}")
    void updateLoginTime(Integer userID, LocalDateTime loginTime);

    // 修改用户权限
    @Update("UPDATE user SET role_id = #{roleID} WHERE user_name = #{userName}")
    void updateRoleID(String userName, Integer roleID);

    // 根据ID查找用户
    @Select("SELECT * FROM user WHERE user_id = #{userID}")
    User selectUserByID(Integer userID);

    // 根据用户名查找用户
    @Select("SELECT * FROM user WHERE user_name = #{userName}")
    User selectUserByName(String userName);

    // 获取当前用户信息
    @Select("SELECT * FROM user WHERE user_id = #{userID}")
    User getCurrentUser(Integer userID);

    // 获取用户总数量(除了当前用户)
    Integer getUserNum(Integer userID, String userName, String userRole);

    // 分页获取所有用户信息（除了当前用户）
    List<User> getAllUserInfo(Integer offset, Integer pageSize, Integer userID, String userName, String userRole);

    // 根据图片查询用户，用于判断图片是否有效
    @Select("SELECT * FROM user WHERE avatar = #{image}")
    User getUserByImage(String image);
}
