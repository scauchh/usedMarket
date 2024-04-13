package org.scau.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.scau.pojo.Trade;

import java.util.List;

@Mapper
public interface TradeMapper {
    // 添加交易
    @Insert("INSERT INTO trade (buyer_name, seller_name, book_id, state, notes) " +
            "VALUES (#{buyerName}, #{sellerName}, #{bookID}, 1, '')")
    void addTrade(String buyerName, String sellerName, Integer bookID);

    // 更新交易状态
    void updateTrade(Integer tradeID, Integer state, String notes);

    // 查找进行中的重复交易
    @Select("SELECT COUNT(*) FROM trade WHERE buyer_name = #{buyerName} " +
            "AND seller_name = #{sellerName} AND book_id = #{bookID} AND (state = 0 OR state = 1)")
    Integer searchTradeByAll(String buyerName, String sellerName, Integer bookID);

    // 获取所有交易的数量
    Integer getAllTradeNum(Integer offset, Integer pageSize, Integer state);

    // 分页获取所有交易
    List<Trade> getAllTrade(Integer offset, Integer pageSize, Integer state);

    // 获取发起的交易的数量
    Integer getTradeFromMeNum(Integer offset, Integer pageSize, String userName, Integer state);

    // 分页获取发起的交易
    List<Trade> getTradeFromMe(Integer offset, Integer pageSize, String userName, Integer state);

    // 获取收到的交易的数量
    Integer getTradeToMeNum(Integer offset, Integer pageSize, String userName, Integer state);

    // 分页获取收到的交易
    List<Trade> getTradeToMe(Integer offset, Integer pageSize, String userName, Integer state);

}
