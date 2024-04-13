package org.scau.pojo;

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
    private String buyerName;     // 买家用户名
    private String sellerName;    // 卖家用户名
    private Integer goodsID;      // 物品ID
    private Integer state;        // 交易状态
    private String notes;         // 交易备注（交易失败时使用）
}
