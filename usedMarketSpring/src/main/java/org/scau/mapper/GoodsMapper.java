package org.scau.mapper;

import org.apache.ibatis.annotations.*;
import org.scau.model.pojo.Goods;
import org.scau.model.vo.GoodsUserView;

import java.util.List;

@Mapper
public interface GoodsMapper {

    // 添加新物品
    @Insert("INSERT INTO goods (user_id, goods_name, picture, price, type, datetime, notes) " +
            "VALUES (#{userID}, #{goodsName}, #{picture}, #{price}, #{type}, #{datetime}, #{notes})")
    void addGoods(Goods goods);

    // 根据物品ID删除物品
    @Update("UPDATE goods SET active = '0' WHERE goods_id = #{goodsID}")
    void deleteGoodsByID(Integer goodsID);

    // 根据用户ID删除物品
    @Update("UPDATE goods SET active = '0' WHERE user_id = #{userID}")
    void deleteGoodsByUserID(Integer userID);

    // 根据物品ID更新物品
    @Update("UPDATE goods SET goods_name = #{goodsName}, picture = #{picture}, price = #{price}, " +
            "type = #{type}, datetime = #{datetime}, notes = #{notes} WHERE goods_id = #{goodsID}")
    void updateGoodsByID(Goods goods);

    // 根据物品ID获取物品
    @Select("SELECT * FROM goods WHERE goods_id = #{goodsID}")
    Goods getGoodsByID(Integer goodsID);

    // 根据图片查询物品，用于判断图片是否有效
    @Select("SELECT COUNT(*) FROM goods WHERE picture = #{image}")
    Integer getGoodsByImage(String image);

    // 获取所有物品的总数
    Integer getAllGoodsNum(String goodsName, String type);

    // 分页获得所有的物品
    List<GoodsUserView> getPageGoods(Integer offset, Integer pageSize, String goodsName, String type);

    // 根据用户ID获取所有物品的数量
    @Select("SELECT COUNT(*) FROM goods WHERE user_id = #{userID} AND active = '1'")
    Integer getGoodsNumByID(Integer userID);

    // 根据用户ID分页获得所有的物品
    List<Goods> getPageGoodsByID(Integer userID, Integer offset, Integer pageSize);
}
