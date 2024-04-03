package org.scau.service;

public interface TradeService {
    // 添加交易
    void addTrade(Integer buyerID, Integer sellerID, Integer bookID);

    // 查找进行中的重复交易
    Integer searchTradeByAll(Integer buyerID, Integer sellerID, Integer bookID);
}
