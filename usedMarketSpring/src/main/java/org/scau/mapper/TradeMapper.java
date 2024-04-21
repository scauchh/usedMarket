package org.scau.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.scau.model.vo.TradeView;

import java.util.List;

@Mapper
public interface TradeMapper {
    // 添加交易
    @Insert("INSERT INTO trade (buyer_id, goods_id, state, notes) VALUES (#{buyerID}, #{goodsID}, 1, '')")
    void addTrade(Integer buyerID, Integer goodsID);

    // 更新交易状态
    void updateTrade(Integer tradeID, Integer state, String notes);

    // 查找进行中的重复交易
    @Select("SELECT COUNT(*) FROM trade WHERE buyer_id = #{buyerID} AND goods_id = #{goodsID} AND (state = 1 OR state = 2)")
    Integer searchTradeByAll(Integer buyerID, Integer goodsID);

    // 获取所有交易的数量
    Integer getAllTradeNum(Integer offset, Integer pageSize, String buyerNickName, String sellerNickName, String goodsName, Integer state);

    // 分页获取所有交易
    List<TradeView> getAllTrade(Integer offset, Integer pageSize, String buyerNickName, String sellerNickName, String goodsName, Integer state);

    // 获取发起的交易的数量
    Integer getTradeFromMeNum(Integer offset, Integer pageSize, Integer userID, String sellerNickName, String goodsName, Integer state);

    // 分页获取发起的交易
    List<TradeView> getTradeFromMe(Integer offset, Integer pageSize, Integer userID, String sellerNickName, String goodsName, Integer state);

    // 获取收到的交易的数量
    Integer getTradeToMeNum(Integer offset, Integer pageSize, Integer userID, String buyerNickName, String goodsName, Integer state);

    // 分页获取收到的交易
    List<TradeView> getTradeToMe(Integer offset, Integer pageSize, Integer userID, String buyerNickName, String goodsName, Integer state);
}
