package org.scau.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.scau.pojo.User;

@Mapper
public interface UserMapper {

    // 添加新用户
    @Insert("INSERT INTO user (user_name, nick_name, password) VALUES (#{userName}, #{userName}, #{password})")
    void addUser(String userName, String password);

    // 更新用户信息
    void updateUser(User u);

    // 根据ID查找用户
    @Select("SELECT * FROM user WHERE user_id = #{userID}")
    User selectUserByID(Integer userID);

    // 根据用户名查找用户
    @Select("SELECT * FROM user WHERE user_name = #{userName}")
    User selectUserByName(String userName);

    // 获取当前用户信息
    @Select("SELECT * FROM user WHERE user_id = #{userID}")
    User getCurrentUser(Integer userID);

    // 根据图片查询用户，用于判断图片是否有效
    @Select("SELECT * FROM user WHERE avatar = #{image}")
    User getUserByImage(String image);
}
