package org.scau.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Trade {
    private Integer tradeID;      // 交易ID
    private String buyerID;       // 买家userID
    private String sellerID;      // 卖家userID
    private Integer goodsID;      // 物品ID
    private Integer state;        // 交易状态
    private String notes;         // 交易备注（交易失败时使用）
}
