package org.scau.service.impl;

import org.scau.mapper.TradeMapper;
import org.scau.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TradeServiceImpl implements TradeService {
    @Autowired
    TradeMapper tradeMapper;

    // 添加交易
    @Override
    public void addTrade(Integer buyerID, Integer sellerID, Integer bookID) {
        tradeMapper.addTrade(buyerID, sellerID, bookID);
    }

    // 查找进行中的重复交易
    @Override
    public Integer searchTradeByAll(Integer buyerID, Integer sellerID, Integer bookID) {
        return tradeMapper.searchTradeByAll(buyerID, sellerID, bookID);
    }
}
