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
    @Insert("INSERT INTO trade (buyer_id, seller_id, book_id, state) " +
            "VALUES (#{buyerID}, #{sellerID}, #{bookID}, 1)")
    void addTrade(Integer buyerID, Integer sellerID, Integer bookID);

    // 更新交易状态
    void updateTrade(Integer tradeID, Integer state, String notes);

    // 查找进行中的重复交易
    @Select("SELECT COUNT(*) FROM trade WHERE buyer_id = #{buyerID} " +
            "AND seller_id = #{sellerID} AND book_id = #{bookID} AND (state = 0 OR state = 1)")
    Integer searchTradeByAll(Integer buyerID, Integer sellerID, Integer bookID);

    // 分页获取所有交易
    List<Trade> getAllTrade(Integer offset, Integer pageSize, Integer state);

    // 分页获取发起的交易
    List<Trade> getTradeFromMe(Integer offset, Integer pageSize, Integer userID, Integer state);

    // 分页获取收到的交易
    List<Trade> getTradeToMe(Integer offset, Integer pageSize, Integer userID, Integer state);

}
