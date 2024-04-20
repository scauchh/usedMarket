package org.scau.model.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer userID;                           // id 唯一标识符
    private String userName;                          // ⽤户名
    private String nickName;                          // 用户昵称
    private String password;                          // 密码
    private String avatar;                            // 头像
    private String gender;                            // 性别
    private String email;                             // 邮箱
    private String phone;                             // 手机
    private String wechat;                            // 微信号
    private String address;                           // 地址
    private Integer roleID;                           // 用户角色
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registerTime;               // 数据更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime loginTime;                  // 数据更新时间
}
