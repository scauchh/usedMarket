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
    private Integer tradeID;    // 交易ID
    private Integer buyerID;    // 买家ID
    private Integer sellerID;   // 卖家ID
    private Integer bookID;     // 书籍ID
    private String state;       // 交易状态
}
