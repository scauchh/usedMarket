package org.scau.controller;

import org.scau.pojo.Result;
import org.scau.pojo.Type;
import org.scau.service.TypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/type")
public class TypeController {

    final Logger logger = LoggerFactory.getLogger(TypeController.class);

    @Autowired
    TypeService typeService;

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
