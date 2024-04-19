package org.scau.service;

import org.scau.model.PageBean;
import org.scau.model.pojo.Trade;
import org.scau.model.vo.TradeGoodsView;

public interface TradeService {
    // 添加交易
    void addTrade(String buyerName, String sellerName, Integer goodsID);

    // 更新交易状态
    void updateTrade(Integer tradeID, Integer state, String notes);

    // 查找进行中的重复交易
    Integer searchTradeByAll(String buyerName, String sellerName, Integer goodsID);

    // 分页获取所有交易
    PageBean<TradeGoodsView> getAllTrade(Integer pageNum, Integer pageSize, Integer state);

    // 分页获取发起的交易
    PageBean<TradeGoodsView> getTradeFromMe(Integer pageNum, Integer pageSize, Integer state, String userName);

    // 分页获取接收的交易
    PageBean<TradeGoodsView> getTradeToMe(Integer pageNum, Integer pageSize, Integer state, String userName);
}
