package org.scau.service;

import org.scau.model.PageBean;
import org.scau.model.vo.TradeView;

public interface TradeService {
    // 添加交易
    void addTrade(Integer buyerID, Integer goodsID);

    // 更新交易状态
    void updateTrade(Integer tradeID, Integer state, String notes);

    // 查找进行中的重复交易
    Integer searchTradeByAll(Integer buyerID, Integer goodsID);

    // 分页获取所有交易
    PageBean<TradeView> getAllTrade(Integer pageNum, Integer pageSize, String buyerNickName, String sellerNickName, String goodsName, Integer state);

    // 分页获取发起的交易
    PageBean<TradeView> getTradeFromMe(Integer pageNum, Integer pageSize, Integer userID, String sellerNickName, String goodsName, Integer state);

    // 分页获取接收的交易
    PageBean<TradeView> getTradeToMe(Integer pageNum, Integer pageSize, Integer userID, String buyerNickName, String goodsName, Integer state);
}
