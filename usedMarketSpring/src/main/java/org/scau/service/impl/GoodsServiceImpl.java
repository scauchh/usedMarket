package org.scau.service.impl;

import org.scau.mapper.GoodsMapper;
import org.scau.pojo.Goods;
import org.scau.pojo.vo.PageBean;
import org.scau.service.GoodsService;
import org.scau.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    GoodsMapper goodsMapper;

    // 添加新物品
    @Override
    public void addGoods(Goods goods) {
        goodsMapper.addGoods(goods);
    }

    // 根据ID删除物品
    @Override
    public void deleteGoodsByID(Integer goodsID) {
        goodsMapper.deleteGoodsByID(goodsID);
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

    // 获取所有的物品
    @Override
    public List<Goods> getAllGoods() {
        return goodsMapper.getAllGoods();
    }

    // 根据物品标题获得物品
    @Override
    public Goods getGoodsByName(String goodsName) {
        return goodsMapper.getGoodsByName(goodsName);
    }

    // 根据图片查询物品，用于判断图片是否有效
    @Override
    public Goods getGoodsByImage(String image) {
        return goodsMapper.getGoodsByImage(image);
    }

    // 根据类型查询物品，用于判断类型是否可以删除
    @Override
    public Integer getGoodsNumByType(String typeName) {
        return goodsMapper.getGoodsNumByType(typeName);
    }

    // 分页获得所有的物品
    @Override
    public PageBean<Goods> getPageGoods(Integer pageNum, Integer pageSize, String goodsName, String type) {
        PageBean<Goods> pb = new PageBean<>();

        // 查询分页结果
        int total = goodsMapper.getAllGoodsNum(goodsName, type);
        List<Goods> goodsList = goodsMapper.getPageGoods(pageSize*(pageNum-1), pageSize, goodsName, type);

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
