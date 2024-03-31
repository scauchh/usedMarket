package org.scau.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private Integer userID;     // id 唯一标识符
    private String userName;    // ⽤户名
    private String nickName;    // 用户昵称
    private String password;    // 密码
    private String avatar;      // 头像
    private String gender;      // 性别
    private String email;       // 邮箱
    private String phone;       // 手机
}
