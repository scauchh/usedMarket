package org.scau.controller;

import org.scau.pojo.model.Result;
import org.scau.pojo.Type;
import org.scau.service.GoodsService;
import org.scau.service.TypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/type")
public class TypeController {

    final Logger logger = LoggerFactory.getLogger(TypeController.class);

    @Autowired
    TypeService typeService;
    @Autowired
    GoodsService bookService;

    // 检验数据
    private String verifyData(Integer typeID, String typeName){
        // 检验类别编号
        if(typeID == null){
            return "类别编号不能为空";
        }

        // 检验类别名称
        if (Objects.equals(typeName, "null") || typeName.isEmpty()) {
            return "类别名称不能为空";
        } else if(typeName.length()>10){
            return "类别名称的长度不能超过10位";
        }

        return "success";
    }

    // 添加书籍类型
    @RequestMapping("/addType")
    public Result addType(Integer typeID, String typeName){
        String result = verifyData(typeID, typeName);
        if(!result.equals("success")) return Result.error(result);

        Type t = typeService.getTypeByID(typeID);
        if(t != null) return Result.error("类别编号已存在");

        t = typeService.getTypeByName(typeName);
        if(t != null) return Result.error("类别名称已存在");

        try {
            typeService.addType(typeID, typeName);
            return Result.success();
        }catch (Exception e){
            logger.error(e.toString());
            return Result.error("添加书籍类型失败");
        }
    }

    // 删除书籍类型
    @RequestMapping("/deleteType")
    public Result deleteType(String typeName){
        try{
            typeService.deleteType(typeName);
            return Result.success();
        }catch (Exception e){
            logger.error(e.toString());
            return Result.error("删除书籍类型失败");
        }
    }

    // 修改书籍类型
    @RequestMapping("/changeType")
    public Result changeType(Integer oldTypeID, String oldTypeName, Integer typeID, String typeName){
        String result = verifyData(typeID, typeName);
        if(!result.equals("success")) return Result.error(result);

        Type t = typeService.getTypeByID(typeID);
        if(t != null && !Objects.equals(t.getTypeID(), oldTypeID)) return Result.error("类别编号已存在");

        t = typeService.getTypeByName(typeName);
        if(t != null && !Objects.equals(t.getTypeName(), oldTypeName)) return Result.error("类别名称已存在");

        try{
            typeService.updateType(oldTypeID, typeID, typeName);
            return Result.success();
        }catch (Exception e){
            logger.error(e.toString());
            return Result.error("修改书籍类型失败");
        }
    }

    // 获取所有书籍类型
    @RequestMapping("/getAllType")
    public Result getAllType(){
        try {
            List<Type> types = typeService.getAllType();
            return Result.success(types);
        }catch (Exception e){
            logger.error(e.toString());
            return Result.error("获取书籍类型失败");
        }
    }
}
