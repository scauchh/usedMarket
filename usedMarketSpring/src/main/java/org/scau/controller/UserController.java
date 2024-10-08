package org.scau.controller;

import org.scau.model.pojo.Relation;
import org.scau.model.PageBean;
import org.scau.model.Result;
import org.scau.model.pojo.User;
import org.scau.service.GoodsService;
import org.scau.service.RelationService;
import org.scau.service.UserService;
import org.scau.utils.JWTUtil;
import org.scau.utils.ThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequestMapping("/user")
@RestController
public class UserController {

    final Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    UserService userService;
    @Autowired
    GoodsService goodsService;
    @Autowired
    RelationService relationService;

    // 验证用户名的格式
    private String verifyUserName(String userName) {
        if (userName == null || userName.isEmpty())
            return "用户名不能为空";
        if (userName.length() < 2 || userName.length() > 16)
            return "请输入正确的用户名";
        return "success";
    }

    // 用户登录
    @RequestMapping("/login")
    public Result login(String userName, String password) {
        String result = verifyUserName(userName);
        if (!result.equals("success")) {
            return Result.error(result);
        }

        User u = userService.searchUserByName(userName);
        if (u == null) {
            return Result.error("用户名不存在");
        } else if (!Objects.equals(u.getPassword(), password)) {
            return Result.error("密码错误");
        }

        try {
            // 更新用户登录时间
            userService.updateLoginTime(u.getUserID(), LocalDateTime.now());

            Map<String, Object> claim = new HashMap<>();
            claim.put("id", u.getUserID());
            claim.put("userName", u.getUserName());

            // 登录成功,返回token
            return Result.success(JWTUtil.genToken(claim));
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error("登录失败");
        }
    }

    // 用户注册
    @RequestMapping("/register")
    public Result register(String userName, String password, String rePassword) {
        String result = verifyUserName(userName);
        if (!result.equals("success")) {
            return Result.error(result);
        }
        if (!password.equals(rePassword)) return Result.error("两次输入密码不一致");

        try {
            User u = userService.searchUserByName(userName);
            if (u != null) return Result.error("用户名已存在");

            LocalDateTime registerTime = LocalDateTime.now();
            userService.addUser(userName, password, registerTime);
            return Result.success();
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error("注册失败");
        }
    }

    // 删除用户
    @RequestMapping("/deleteUser")
    public Result deleteUser(Integer userID) {
        try {
            userService.deleteUser(userID);
            goodsService.deleteGoodsByUserID(userID);
            return Result.success();
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error("删除用户失败");
        }
    }

    // 修改密码
    @RequestMapping("/updatePassward")
    public Result updatePassword(String password, String rePassword) {
        if (!password.equals(rePassword)) return Result.error("两次输入密码不一致");

        try {
            Map<String, Object> map = ThreadLocalUtil.get();
            Integer userID = (Integer) map.get("id");

            userService.updatePassword(userID, password);

            return Result.success();
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error("修改密码失败");
        }
    }

    // 管理员重置用户密码
    @RequestMapping("/resetPasswordByManage")
    public Result resetPasswordByManage(String userName) {
        try {
            User user = userService.searchUserByName(userName);
            if (Objects.equals(user,null)) return Result.error("该用户不存在");
            // MD5加密
            String s = "123456";
            String passWard = DigestUtils.md5DigestAsHex(s.getBytes());
            userService.updatePassword(user.getUserID(), passWard);
            return Result.success();
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error("重置密码失败");
        }
    }

    // 重置用户密码
    @RequestMapping("/resetPassword")
    public Result resetPassword(String userName, Integer questionID, String answer) {
        String result = verifyUserName(userName);
        if (!result.equals("success")) {
            return Result.error(result);
        }
        if (questionID == null) return Result.error("请选择密保问题");
        if (answer == null || answer.isEmpty()) return Result.error("答案不能为空");

        try {
            User user = userService.searchUserByName(userName);
            if (user == null) return Result.error("该用户不存在");

            Integer userID = user.getUserID();
            Relation relation = relationService.searchRelationByID(userID);

            if (relation == null) return Result.error("您并没有设置密保问题");
            if (!Objects.equals(relation.getQuestionID(), questionID)) return Result.error("你的密保问题选择错误");
            if (!Objects.equals(relation.getAnswer(), answer)) return Result.error("答案错误");

            userService.updatePassword(userID, "e10adc3949ba59abbe56e057f20f883e");

            return Result.success();
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error("重置密码失败");
        }
    }

    // 获取当前用户信息
    @RequestMapping("/getUserInfo")
    public Result getUserInfo() {
        try {
            Map<String, Object> map = ThreadLocalUtil.get();
            User u = userService.searchUserByID(Integer.parseInt(map.get("id").toString()));
            return Result.success(u);
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error("获取当前用户信息失败");
        }
    }

    // 根据用户ID获取用户信息
    @RequestMapping("/getUserInfoByID")
    public Result getUserInfoByID(Integer userID) {
        try {
            User u = userService.searchUserByID(userID);
            return Result.success(u);
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error("获取用户信息失败");
        }
    }

    // 根据用户名获取用户信息
    @RequestMapping("/getUserInfoByName")
    public Result getUserInfoByName(String userName) {
        try {
            User u = userService.searchUserByName(userName);
            return Result.success(u);
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error("获取用户信息失败");
        }
    }

    // 分页获取所有用户信息
    @RequestMapping("/getAllUserInfo")
    public Result getAllUserInfo(Integer pageNum, Integer pageSize, String userName, String userRole) {
        try {
            Map<String, java.lang.Object> claim = ThreadLocalUtil.get();
            Integer userID = Integer.valueOf(claim.get("id").toString());

            PageBean<User> users = userService.getAllUserInfo(pageNum, pageSize, userID, userName, userRole);
            return Result.success(users);
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error("获取所有用户信息失败");
        }
    }

    // 更新用户信息
    @RequestMapping("/updateUser")
    public Result updateUser(@RequestBody User user) {
        String nickName = user.getNickName();
        String email = user.getEmail();
        String phone = user.getPhone();
        String wechat = user.getWechat();
        String address = user.getAddress();

        if (nickName == null || nickName.isEmpty()) {
            return Result.error("昵称不能为空");
        }
        if (nickName.length() < 2 || nickName.length() > 16) {
            return Result.error("昵称的长度必须为2~16位");
        }

        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        if (email != null && !email.isEmpty() && !email.matches(emailRegex)) {
            return Result.error("请输入正确的邮箱");
        }

        String phoneRegex = "^1[3-9]\\d{9}$";
        if (phone != null && !phone.isEmpty() && !phone.matches(phoneRegex)) {
            return Result.error("请输入正确的手机号码");
        }

        if(wechat != null && wechat.length() > 20){
            return Result.error("微信号的长度不能超过20位");
        }

        System.out.println("a: "+address);
        if(address != null && address.length() > 20){
            return Result.error("地址的长度不能超过20位");
        }

        try {
            userService.updateUser(user);
            return Result.success(user);
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error("更新用户信息失败");
        }
    }

    // 修改用户权限
    @RequestMapping("/updateUserRole")
    public Result updateUserRole(String userName, String userRole) {
        try {
            User user = userService.searchUserByName(userName);
            if(Objects.equals(user, null)) return Result.error("用户不存在");

            userService.updateRole(userName, Objects.equals(userRole, "管理员") ? 1 : 0);
            return Result.success();
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error("修改用户权限失败");
        }
    }
}
