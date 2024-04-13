package org.scau.service.impl;

import org.scau.mapper.TradeMapper;
import org.scau.pojo.PageBean;
import org.scau.pojo.Trade;
import org.scau.service.TradeService;
import org.scau.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TradeServiceImpl implements TradeService {
    @Autowired
    TradeMapper tradeMapper;

    // 添加交易
    @Override
    public void addTrade(String buyerName, String sellerName, Integer bookID) {
        tradeMapper.addTrade(buyerName, sellerName, bookID);
    }

    // 更新交易状态
    @Override
    public void updateTrade(Integer tradeID, Integer state, String notes) {
        System.out.println("notes: "+notes);
        tradeMapper.updateTrade(tradeID, state, notes);
    }

    // 查找进行中的重复交易
    @Override
    public Integer searchTradeByAll(String buyerName, String sellerName, Integer bookID) {
        return tradeMapper.searchTradeByAll(buyerName, sellerName, bookID);
    }

    // 分页获取所有交易
    @Override
    public PageBean<Trade> getAllTrade(Integer pageNum, Integer pageSize, Integer state) {
        PageBean<Trade> pb = new PageBean<>();

        // 查询分页结果
        Integer total = tradeMapper.getAllTradeNum(pageSize*(pageNum-1), pageSize, state);
        List<Trade> trades = tradeMapper.getAllTrade(pageSize*(pageNum-1), pageSize, state);

        // 返回分页查询结果
        pb.setTotal(total);
        pb.setItems(trades);
        return pb;
    }

    // 分页获取发起的交易
    @Override
    public PageBean<Trade> getTradeFromMe(Integer pageNum, Integer pageSize, Integer state) {
        PageBean<Trade> pb = new PageBean<>();

        // 获取用户名
        Map<String, Object> map = ThreadLocalUtil.get();
        String userName = map.get("userName").toString();

        // 查询分页结果
        Integer total = tradeMapper.getTradeFromMeNum(pageSize*(pageNum-1), pageSize, userName, state);
        List<Trade> trades = tradeMapper.getTradeFromMe(pageSize*(pageNum-1), pageSize, userName, state);

        System.out.println("a: "+total);
        // 返回分页查询结果
        pb.setTotal(total);
        pb.setItems(trades);
        return pb;
    }

    // 分页获取接收的交易
    @Override
    public PageBean<Trade> getTradeToMe(Integer pageNum, Integer pageSize, Integer state) {
        PageBean<Trade> pb = new PageBean<>();

        // 获取用户名
        Map<String, Object> map = ThreadLocalUtil.get();
        String userName = map.get("userName").toString();

        // 查询分页结果
        Integer total = tradeMapper.getTradeToMeNum(pageSize*(pageNum-1), pageSize, userName, state);
        List<Trade> trades = tradeMapper.getTradeToMe(pageSize*(pageNum-1), pageSize, userName, state);

        // 返回分页查询结果
        pb.setTotal(total);
        pb.setItems(trades);
        return pb;
    }
}
