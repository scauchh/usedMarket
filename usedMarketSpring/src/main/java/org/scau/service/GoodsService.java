package org.scau.service;

import org.scau.pojo.Goods;
import org.scau.pojo.model.PageBean;

import java.util.List;

public interface GoodsService {

    // 添加新物品
    void addGoods(Goods goods);

    // 根据ID删除物品
    void deleteGoodsByID(Integer goodsID);

    // 根据物品ID更新物品
    void updateGoodsByID(Goods goods);

    // 根据ID查找物品
    Goods getGoodsByID(Integer goodsID);

    // 获取所有的物品
    List<Goods> getAllGoods();

    // 根据物品标题获得物品
    Goods getGoodsByName(String goodsName);

    // 根据图片查询物品，用于判断图片是否有效
    Goods getGoodsByImage(String image);

    // 根据类型查询物品，用于判断类型是否可以删除
    Integer getGoodsNumByType(String typeName);

    // 分页获得所有的物品
    PageBean<Goods> getPageGoods(Integer pageNum, Integer pageSize, String goodsName, String type);

    // 根据用户ID分页获得所有的物品
    PageBean<Goods> getPageGoodsByID(Integer pageNum, Integer pageSize);
}
