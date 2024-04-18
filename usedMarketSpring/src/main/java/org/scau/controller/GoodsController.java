package org.scau.controller;

import org.scau.pojo.Goods;
import org.scau.pojo.vo.PageBean;
import org.scau.pojo.vo.Result;
import org.scau.service.GoodsService;
import org.scau.utils.ThreadLocalUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    final Logger logger = LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    GoodsService goodsService;

    // 检验数据
    private String verifyData(Goods goods){
        String goodsName = goods.getGoodsName();
        String picture = goods.getPicture();
        Double price = goods.getPrice();
        String type = goods.getType();
        String notes = goods.getNotes();

        // 检验标题
        if (goodsName == null || goodsName.isEmpty()) {
            return "物品名称不能为空";
        }
        if (goodsName.length() < 2 || goodsName.length() > 25) {
            return "物品名称的长度必须为2~25位";
        }

        // 检验图片
        if (picture == null || picture.isEmpty()) {
            return "图片不能为空";
        }

        // 检验价格
        if (price == null) {
            return "价格不能为空";
        }

        // 检验类型
        if (type == null || type.isEmpty()) {
            return "类型不能为空";
        }

        // 检验备注
        if (notes == null) goods.setNotes("");
        if (notes != null && notes.length() > 200) {
            return "备注的长度不能超过100位";
        }

        // 获取当前时间
        goods.setDatetime(LocalDateTime.now());

        return "success";
    }

    // 添加物品
    @RequestMapping("/addGoods")
    public Result addGoods(Goods goods) {
        String result = verifyData(goods);
        if(!result.equals("success")) return Result.error(result);

        try {
            Map<String, java.lang.Object> claim = ThreadLocalUtil.get();
            goods.setUserID(Integer.valueOf(claim.get("id").toString()));

            goodsService.addGoods(goods);
            return Result.success();
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error("添加物品失败");
        }
    }

    // 删除物品
    @RequestMapping("/deleteGoods")
    public Result deleteGoods(Integer goodsID) {
        try {
            goodsService.deleteGoodsByID(goodsID);
            return Result.success();
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error("删除物品失败");
        }
    }

    // 更新物品
    @RequestMapping("/updateGoods")
    public Result updateGoodsByID(Goods goods) {
        String result = verifyData(goods);
        if(!result.equals("success")) return Result.error(result);

        try {
            goodsService.updateGoodsByID(goods);
            return Result.success();
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error("修改失败");
        }
    }

    // 根据物品ID查找物品
    @RequestMapping("/getGoodsByID")
    public Result getGoodsByID(Integer goodsID) {
        try {
            Goods goods = goodsService.getGoodsByID(goodsID);
            return Result.success(goods);
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error("获取失败");
        }
    }

    // 分页获得所有的物品
    @RequestMapping("/getPageGoods")
    public Result getPageGoods(String pageNum, String pageSize, String goodsName, String type) {
        try {
            PageBean<Goods> pb = goodsService.getPageGoods(Integer.parseInt(pageNum), Integer.parseInt(pageSize), goodsName, type);
            return Result.success(pb);
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error("获取失败");
        }
    }

    // 根据用户ID分页获得该用户的所有物品
    @RequestMapping("/getCurrentPageGoods")
    public Result getCurrentPageGoods(String pageNum, String pageSize) {
        try {
            // 获取userID
            Map<String, java.lang.Object> map = ThreadLocalUtil.get();
            Integer userID = (Integer) map.get("id");

            PageBean<Goods> pb = goodsService.getPageGoodsByID(Integer.parseInt(pageNum), Integer.parseInt(pageSize), userID);
            return Result.success(pb);
        } catch (Exception e) {
            logger.error(e.toString());
            return Result.error("获取失败");
        }
    }
}
