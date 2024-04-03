package org.scau.controller;

import org.scau.pojo.Result;
import org.scau.service.TradeService;
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

    final Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    TradeService tradeService;

    // 添加交易
    @RequestMapping("/addTrade")
    public Result addTrade(Integer sellerID, Integer bookID) {
        // 获取当前用户ID
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer buyerID = Integer.parseInt(map.get("id").toString());

        // 检查交易双方的身份
        if(buyerID.equals(sellerID)){
            return Result.error("你不能购买自己的书籍");
        }

        // 检查是否有重复的进行中的交易
        if(tradeService.searchTradeByAll(buyerID, sellerID, bookID)!=0) {
            return Result.error("请勿重复发起交易");
        }

        try{
            tradeService.addTrade(buyerID, sellerID, bookID);
            return Result.success();
        }catch (Exception e){
            logger.error(e.toString());
            return Result.error("添加交易失败");
        }
    }
}
