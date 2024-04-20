package org.scau.model.vo;

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
public class GoodsUserView {
    private Integer goodsID;                          // 物品ID
    private Integer userID;                           // 用户ID
    private String goodsName;                         // 物品名称
    private String picture;                           // 物品图片
    private Double price;                             // 物品价格
    private String type;                              // 物品类型
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime datetime;                   // 数据更新时间
    private String notes;                             // 备注
    private String nickName;                          // 用户昵称
    private String avatar;                            // 用户头像
}
