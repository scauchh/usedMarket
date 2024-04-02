package org.scau.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.scau.pojo.PageBean;
import org.scau.pojo.User;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface UserMapper {

    // 添加新用户
    @Insert("INSERT INTO user (user_name, nick_name, password, register_time, login_time) " +
            "VALUES (#{userName}, #{userName}, #{password}, #{registerTime}, #{registerTime})")
    void addUser(String userName, String password, LocalDateTime registerTime);

    // 更新用户信息
    void updateUser(User u);

    // 更新用户登录时间
    @Update("UPDATE user SET login_time = #{loginTime} WHERE user_id = #{userID}")
    void updateLoginTime(Integer userID, LocalDateTime loginTime);

    // 根据ID查找用户
    @Select("SELECT * FROM user WHERE user_id = #{userID}")
    User selectUserByID(Integer userID);

    // 根据用户名查找用户
    @Select("SELECT * FROM user WHERE user_name = #{userName}")
    User selectUserByName(String userName);

    // 获取当前用户信息
    @Select("SELECT * FROM user WHERE user_id = #{userID}")
    User getCurrentUser(Integer userID);

    // 获取用户总数量
    @Select("SELECT COUNT(*) FROM user")
    Integer getUserNum();

    // 分页获取所有用户信息
    @Select("SELECT * FROM user limit #{offset}, #{pageSize}")
    List<User> getAllUserInfo(Integer offset, Integer pageSize);

    // 根据图片查询用户，用于判断图片是否有效
    @Select("SELECT * FROM user WHERE avatar = #{image}")
    User getUserByImage(String image);
}
