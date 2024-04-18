package org.scau.mapper;

import org.apache.ibatis.annotations.*;
import org.scau.pojo.Goods;

import java.util.List;

@Mapper
public interface GoodsMapper {

    // 添加新物品
    @Insert("INSERT INTO goods (user_id, goods_name, picture, price, type, datetime, notes) " +
            "VALUES (#{userID}, #{goodsName}, #{picture}, #{price}, #{type}, #{datetime}, #{notes})")
    void addGoods(Goods goods);

    // 根据goodsID删除物品
    @Update("UPDATE goods SET active = '0' WHERE goods_id = #{goodsID}")
    void deleteGoodsByID(Integer goodsID);

    // 根据userID删除物品
    @Update("UPDATE goods SET active = '0' WHERE user_id = #{userID}")
    void deleteGoodsByUserID(Integer userID);

    // 根据物品ID更新物品
    @Update("UPDATE goods SET goods_name = #{goodsName}, picture = #{picture}, price = #{price}, " +
            "type = #{type}, datetime = #{datetime}, notes = #{notes} WHERE goods_id = #{goodsID}")
    void updateGoodsByID(Goods goods);

    // 根据ID查找物品
    @Select("SELECT * FROM goods WHERE goods_id = #{goodsID}")
    Goods getGoodsByID(Integer goodsID);

    // 获取所有的物品
    @Select("SELECT * FROM goods WHERE active = '1';")
    List<Goods> getAllGoods();

    // 获取所有物品的总数
    Integer getAllGoodsNum(String goodsName, String type);

    // 获取当前用户所有物品的数量
    @Select("SELECT COUNT(*) FROM goods WHERE user_id = #{userID} AND active = '1'")
    Integer getGoodsNumByID(Integer userID);

    // 根据物品名称获得物品
    @Select("SELECT * FROM goods WHERE goods_name = #{goodsName}")
    Goods getGoodsByName(String goodsName);

    // 根据图片查询物品，用于判断图片是否有效
    @Select("SELECT * FROM goods WHERE picture = #{image}")
    Goods getGoodsByImage(String image);

    // 根据类型查询物品，用于判断类型是否可以删除
    @Select("SELECT COUNT(*) FROM goods WHERE type = #{typeName} AND active = '1'")
    Integer getGoodsNumByType(String typeName);

    // 分页获得所有的物品
    List<Goods> getPageGoods(Integer offset, Integer pageSize, String goodsName, String type);

    // 根据用户ID分页获得所有的物品
    List<Goods> getPageGoodsByID(Integer userID, Integer offset, Integer pageSize);
}
