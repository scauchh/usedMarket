package org.scau.service.impl;

import org.scau.mapper.TradeMapper;
import org.scau.model.PageBean;
import org.scau.model.vo.TradeView;
import org.scau.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {
    @Autowired
    TradeMapper tradeMapper;

    // 添加交易
    @Override
    public void addTrade(Integer buyerID, Integer sellerID, Integer goodsID) {
        tradeMapper.addTrade(buyerID, sellerID, goodsID);
    }

    // 更新交易状态
    @Override
    public void updateTrade(Integer tradeID, Integer state, String notes) {
        tradeMapper.updateTrade(tradeID, state, notes);
    }

    // 查找进行中的重复交易
    @Override
    public Integer searchTradeByAll(Integer buyerID, Integer sellerID, Integer goodsID) {
        return tradeMapper.searchTradeByAll(buyerID, sellerID, goodsID);
    }

    // 分页获取所有交易
    @Override
    public PageBean<TradeView> getAllTrade(Integer pageNum, Integer pageSize, String buyerNickName, String sellerNickName, String goodsName, Integer state) {
        PageBean<TradeView> pb = new PageBean<>();

        // 查询分页结果
        Integer total = tradeMapper.getAllTradeNum(pageSize*(pageNum-1), pageSize, buyerNickName, sellerNickName, goodsName, state);
        List<TradeView> trades = tradeMapper.getAllTrade(pageSize*(pageNum-1), pageSize, buyerNickName, sellerNickName, goodsName, state);

        // 返回分页查询结果
        pb.setTotal(total);
        pb.setItems(trades);
        return pb;
    }

    // 分页获取发起的交易
    @Override
    public PageBean<TradeView> getTradeFromMe(Integer pageNum, Integer pageSize, Integer userID, String sellerNickName, String goodsName, Integer state) {
        PageBean<TradeView> pb = new PageBean<>();

        // 查询分页结果
        Integer total = tradeMapper.getTradeFromMeNum(pageSize*(pageNum-1), pageSize, userID, sellerNickName, goodsName, state);
        List<TradeView> trades = tradeMapper.getTradeFromMe(pageSize*(pageNum-1), pageSize, userID, sellerNickName, goodsName, state);

        // 返回分页查询结果
        pb.setTotal(total);
        pb.setItems(trades);
        return pb;
    }

    // 分页获取接收的交易
    @Override
    public PageBean<TradeView> getTradeToMe(Integer pageNum, Integer pageSize, Integer userID, String buyerNickName, String goodsName, Integer state) {
        PageBean<TradeView> pb = new PageBean<>();

        // 查询分页结果
        Integer total = tradeMapper.getTradeToMeNum(pageSize*(pageNum-1), pageSize, userID, buyerNickName, goodsName, state);
        List<TradeView> trades = tradeMapper.getTradeToMe(pageSize*(pageNum-1), pageSize, userID, buyerNickName, goodsName, state);

        // 返回分页查询结果
        pb.setTotal(total);
        pb.setItems(trades);
        return pb;
    }
}
