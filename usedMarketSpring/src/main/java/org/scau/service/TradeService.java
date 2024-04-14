package org.scau.service;

import org.scau.pojo.model.PageBean;
import org.scau.pojo.Trade;

public interface TradeService {
    // 添加交易
    void addTrade(String buyerName, String sellerName, Integer bookID);

    // 更新交易状态
    void updateTrade(Integer tradeID, Integer state, String notes);

    // 查找进行中的重复交易
    Integer searchTradeByAll(String buyerName, String sellerName, Integer bookID);

    // 分页获取所有交易
    PageBean<Trade> getAllTrade(Integer pageNum, Integer pageSize, Integer state);

    // 分页获取发起的交易
    PageBean<Trade> getTradeFromMe(Integer pageNum, Integer pageSize, Integer state);

    // 分页获取接收的交易
    PageBean<Trade> getTradeToMe(Integer pageNum, Integer pageSize, Integer state);
}
