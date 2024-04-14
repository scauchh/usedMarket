package org.scau.controller;

import org.scau.pojo.model.PageBean;
import org.scau.pojo.model.Result;
import org.scau.pojo.Trade;
import org.scau.service.TradeService;
import org.scau.service.UserService;
import org.scau.utils.ThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/trade")
public class TradeController {

    final Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    TradeService tradeService;
    @Autowired
    UserService userService;

    // 添加交易
    @RequestMapping("/addTrade")
    public Result addTrade(Integer sellerID, Integer bookID) {
        // 获取当前用户的用户名
        Map<String, Object> map = ThreadLocalUtil.get();
        String buyerName = map.get("userName").toString();

        // 获取卖家的用户名
        String sellerName = userService.searchUserByID(sellerID).getUserName();

        // 检查交易双方的身份
        if(buyerName.equals(sellerName)){
            return Result.error("你不能购买自己的书籍");
        }

        // 检查是否有重复的进行中的交易
        if(tradeService.searchTradeByAll(buyerName, sellerName, bookID)!=0) {
            return Result.error("请勿重复发起交易");
        }

        try{
            tradeService.addTrade(buyerName, sellerName, bookID);
            return Result.success();
        }catch (Exception e){
            logger.error(e.toString());
            return Result.error("添加交易失败");
        }
    }

    // 更新交易状态
    @RequestMapping("/updateTrade")
    public Result updateTrade(Integer tradeID, Integer state, String notes) {
        try{
            tradeService.updateTrade(tradeID, state, notes);
            return Result.success();
        }catch (Exception e){
            logger.error(e.toString());
            return Result.error("更新交易数据失败");
        }
    }

    // 分页获取所有交易
    @RequestMapping("/getAllTrade")
    public Result getAllTrade(Integer pageNum, Integer pageSize, Integer state) {
        try{
            PageBean<Trade> trades = tradeService.getAllTrade(pageNum, pageSize, state);
            return Result.success(trades);
        }catch (Exception e){
            logger.error(e.toString());
            return Result.error("获取交易数据失败");
        }
    }

    // 分页获取发起的交易
    @RequestMapping("/getTradeFromMe")
    public Result getTradeFromMe(Integer pageNum, Integer pageSize, Integer state) {
        try{
            PageBean<Trade> trades = tradeService.getTradeFromMe(pageNum, pageSize, state);
            return Result.success(trades);
        }catch (Exception e){
            logger.error(e.toString());
            return Result.error("获取交易数据失败");
        }
    }

    // 分页获取接收的交易
    @RequestMapping("/getTradeToMe")
    public Result getTradeToMe(Integer pageNum, Integer pageSize, Integer state) {
        try{
            PageBean<Trade> trades = tradeService.getTradeToMe(pageNum, pageSize, state);
            return Result.success(trades);
        }catch (Exception e){
            logger.error(e.toString());
            return Result.error("获取交易数据失败");
        }
    }
}
