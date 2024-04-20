package org.scau.controller;

import org.scau.model.PageBean;
import org.scau.model.Result;
import org.scau.model.vo.TradeView;
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
    public Result addTrade(Integer sellerID, Integer goodsID) {
        // 获取当前用户的userID
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer buyerID = (Integer) map.get("id");

//        // 获取卖家的用户名
//        Integer sellerID = userService.searchUserByID(sellerID).getUserID();

        // 检查交易双方的身份
        if(buyerID.equals(sellerID)){
            return Result.error("你不能购买自己的物品");
        }

        // 检查是否有重复的进行中的交易
        if(tradeService.searchTradeByAll(buyerID, sellerID, goodsID)!=0) {
            return Result.error("请勿重复发起交易");
        }

        try{
            tradeService.addTrade(buyerID, sellerID, goodsID);
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
            PageBean<TradeView> trades = tradeService.getAllTrade(pageNum, pageSize, state);
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
            // 获取userID
            Map<String, Object> map = ThreadLocalUtil.get();
            Integer userID = (Integer) map.get("id");

            PageBean<TradeView> trades = tradeService.getTradeFromMe(pageNum, pageSize, state, userID);
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
            // 获取userID
            Map<String, Object> map = ThreadLocalUtil.get();
            Integer userID = (Integer) map.get("id");

            PageBean<TradeView> trades = tradeService.getTradeToMe(pageNum, pageSize, state, userID);
            return Result.success(trades);
        }catch (Exception e){
            logger.error(e.toString());
            return Result.error("获取交易数据失败");
        }
    }
}
