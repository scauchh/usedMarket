package org.scau.controller;

import org.scau.pojo.Result;
import org.scau.pojo.User;
import org.scau.service.UserService;
import org.scau.utils.JWTUtil;
import org.scau.utils.ThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequestMapping("/user")
@RestController
public class UserController {

    final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    UserService userService;

    // 用户登录
    @RequestMapping("/login")
    public Result login(String userName, String password) {
        User u = userService.searchUserByName(userName);
        if (u == null) {
            return Result.error("用户名不存在");
        } else if (!Objects.equals(u.getPassword(), password)) {
            return Result.error("密码错误");
        }

        Map<String, Object> claim = new HashMap<>();
        claim.put("id", u.getUserID());
        claim.put("userName", u.getUserName());

        // 登录成功,返回token
        return Result.success(JWTUtil.genToken(claim));
    }

    // 用户注册
    @RequestMapping("/register")
    public Result register(String userName, String password, String rePassword) {
        User u = userService.searchUserByName(userName);
        if (u == null) {
            if (userName == null || userName.length() < 5 || userName.length() > 16)
                return Result.error("请输入合法的用户名");
            if (password == null || password.length() < 5 || password.length() > 16)
                return Result.error("请输入合法的密码");
            if (!password.equals(rePassword)) return Result.error("两次输入密码不一致");
            try {
                userService.addUser(userName, password);
                return Result.success();
            } catch (Exception e) {
                logger.error(e.toString());
                return Result.error("注册失败");
            }
        } else {
            return Result.error("用户名已存在");
        }
    }

    // 获取当前用户信息
    @RequestMapping("/getUserInfo")
    public Result getUserInfo() {
        try {
            Map<String, Object> map = ThreadLocalUtil.get();
            User u = userService.getCurrentUser(Integer.parseInt(map.get("id").toString()));
            return Result.success(u);
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error("获取当前用户信息失败");
        }
    }

    // 根据用户ID获取当前用户信息
    @RequestMapping("/getUserInfoByID")
    public Result getUserInfoByID(Integer userID) {
        try {
            System.out.println("id: "+userID);
            User u = userService.searchUserByID(userID);
            return Result.success(u);
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error("获取用户信息失败");
        }
    }

    // 更新用户数据
    @RequestMapping("/updateUser")
    public Result updateUser(@RequestBody User user) {
        try {
            userService.updateUser(user);
            return Result.success(user);
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error("更新用户信息失败");
        }
    }
}
