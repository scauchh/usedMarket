package org.scau.service.impl;

import org.scau.mapper.TradeMapper;
import org.scau.model.PageBean;
import org.scau.model.pojo.Trade;
import org.scau.model.vo.TradeGoodsView;
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
    public void addTrade(String buyerName, String sellerName, Integer goodsID) {
        tradeMapper.addTrade(buyerName, sellerName, goodsID);
    }

    // 更新交易状态
    @Override
    public void updateTrade(Integer tradeID, Integer state, String notes) {
        tradeMapper.updateTrade(tradeID, state, notes);
    }

    // 查找进行中的重复交易
    @Override
    public Integer searchTradeByAll(String buyerName, String sellerName, Integer goodsID) {
        return tradeMapper.searchTradeByAll(buyerName, sellerName, goodsID);
    }

    // 分页获取所有交易
    @Override
    public PageBean<TradeGoodsView> getAllTrade(Integer pageNum, Integer pageSize, Integer state) {
        PageBean<TradeGoodsView> pb = new PageBean<>();

        // 查询分页结果
        Integer total = tradeMapper.getAllTradeNum(pageSize*(pageNum-1), pageSize, state);
        List<TradeGoodsView> trades = tradeMapper.getAllTrade(pageSize*(pageNum-1), pageSize, state);

        // 返回分页查询结果
        pb.setTotal(total);
        pb.setItems(trades);
        return pb;
    }

    // 分页获取发起的交易
    @Override
    public PageBean<TradeGoodsView> getTradeFromMe(Integer pageNum, Integer pageSize, Integer state, String userName) {
        PageBean<TradeGoodsView> pb = new PageBean<>();

        // 查询分页结果
        Integer total = tradeMapper.getTradeFromMeNum(pageSize*(pageNum-1), pageSize, userName, state);
        List<TradeGoodsView> trades = tradeMapper.getTradeFromMe(pageSize*(pageNum-1), pageSize, userName, state);

        // 返回分页查询结果
        pb.setTotal(total);
        pb.setItems(trades);
        return pb;
    }

    // 分页获取接收的交易
    @Override
    public PageBean<TradeGoodsView> getTradeToMe(Integer pageNum, Integer pageSize, Integer state, String userName) {
        PageBean<TradeGoodsView> pb = new PageBean<>();

        // 查询分页结果
        Integer total = tradeMapper.getTradeToMeNum(pageSize*(pageNum-1), pageSize, userName, state);
        List<TradeGoodsView> trades = tradeMapper.getTradeToMe(pageSize*(pageNum-1), pageSize, userName, state);

        // 返回分页查询结果
        pb.setTotal(total);
        pb.setItems(trades);
        return pb;
    }
}
