package org.scau.controller;

import org.scau.pojo.Result;
import org.scau.pojo.Type;
import org.scau.service.BookService;
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
    BookService bookService;

    // 添加书籍类型
    @RequestMapping("/addType")
    public Result addType(Integer typeID, String typeName){
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
        System.out.println(oldTypeID + " a "+ oldTypeName+ " b " +typeID + " c " + typeName);
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
