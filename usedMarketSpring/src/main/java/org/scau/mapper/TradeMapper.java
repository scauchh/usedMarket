package org.scau.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface TradeMapper {
    // 添加交易
    @Insert("INSERT INTO trade (buyer_id, seller_id, book_id, state) " +
            "VALUES (#{buyerID}, #{sellerID}, #{bookID}, '0')")
    void addTrade(Integer buyerID, Integer sellerID, Integer bookID);

    // 查找进行中的重复交易
    @Select("SELECT COUNT(*) FROM trade WHERE buyer_id = #{buyerID} " +
            "AND seller_id = #{sellerID} AND book_id = #{bookID} AND (state = '0' OR state = '1')")
    Integer searchTradeByAll(Integer buyerID, Integer sellerID, Integer bookID);
}
