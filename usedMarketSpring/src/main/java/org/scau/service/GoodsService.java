package org.scau.service;

import org.scau.model.pojo.Goods;
import org.scau.model.PageBean;
import org.scau.model.vo.GoodsUserView;

import java.util.List;

public interface GoodsService {

    // 添加新物品
    void addGoods(Goods goods);

    // 根据goodsID删除物品
    void deleteGoodsByID(Integer goodsID);

    // 根据userID删除物品
    void deleteGoodsByUserID(Integer userID);

    // 根据物品ID更新物品
    void updateGoodsByID(Goods goods);

    // 根据ID查找物品
    Goods getGoodsByID(Integer goodsID);

    // 分页获得所有的物品
    PageBean<GoodsUserView> getPageGoods(Integer pageNum, Integer pageSize, String goodsName, String type);

    // 根据用户ID分页获得所有的物品
    PageBean<Goods> getPageGoodsByID(Integer pageNum, Integer pageSize, Integer userID);

    // 根据图片路径查询物品（用于判断图片是否有效）
    Integer getGoodsByImage(String image);
}
