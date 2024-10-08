package org.scau.service.impl;

import org.scau.mapper.GoodsMapper;
import org.scau.model.pojo.Goods;
import org.scau.model.PageBean;
import org.scau.model.vo.GoodsUserView;
import org.scau.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsMapper goodsMapper;

    // 添加新物品
    @Override
    public void addGoods(Goods goods) {
        goodsMapper.addGoods(goods);
    }

    // 根据goodsID删除物品
    @Override
    public void deleteGoodsByID(Integer goodsID) {
        goodsMapper.deleteGoodsByID(goodsID);
    }

    // 根据userID删除物品
    @Override
    public void deleteGoodsByUserID(Integer userID) {
        goodsMapper.deleteGoodsByUserID(userID);
    }

    // 根据物品ID更新物品
    @Override
    public void updateGoodsByID(Goods goods) {
        goodsMapper.updateGoodsByID(goods);
    }

    // 根据ID查找物品
    @Override
    public Goods getGoodsByID(Integer goodsID) {
        return goodsMapper.getGoodsByID(goodsID);
    }

    // 根据图片查询物品，用于判断图片是否有效
    @Override
    public Integer getGoodsByImage(String image) {
        return goodsMapper.getGoodsByImage(image);
    }

    // 分页获得所有的物品
    @Override
    public PageBean<GoodsUserView> getPageGoods(Integer pageNum, Integer pageSize, String goodsName, String type) {
        PageBean<GoodsUserView> pb = new PageBean<>();

        // 查询分页结果
        int total = goodsMapper.getAllGoodsNum(goodsName, type);
        List<GoodsUserView> goodsList = goodsMapper.getPageGoods(pageSize*(pageNum-1), pageSize, goodsName, type);

        // 返回分页查询结果
        pb.setTotal(total);
        pb.setItems(goodsList);
        return pb;
    }

    // 根据用户ID分页获得所有的物品
    @Override
    public PageBean<Goods> getPageGoodsByID(Integer pageNum, Integer pageSize, Integer userID) {
        PageBean<Goods> pb = new PageBean<>();

        // 通过userID查询分页结果
        int total = goodsMapper.getGoodsNumByID(userID);
        List<Goods> goodsList = goodsMapper.getPageGoodsByID(userID, pageSize*(pageNum-1), pageSize);

        // 返回分页查询结果
        pb.setTotal(total);
        pb.setItems(goodsList);
        return pb;
    }
}
