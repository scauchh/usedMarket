package org.scau.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TradeView {
    private Integer tradeID;        // 交易ID
    private String buyerID;         // 买家userID
    private String buyerNickName;   // 买家昵称
    private String buyerAvatar;     // 买家头像
    private String sellerID;        // 买家userID
    private String sellerNickName;  // 买家昵称
    private String sellerAvatar;    // 买家头像
    private Integer goodsID;        // 物品ID
    private String goodsName;       // 物品名称
    private String picture;         // 物品图片
    private Integer state;          // 交易状态
    private String notes;           // 交易备注（交易失败时使用）
}
