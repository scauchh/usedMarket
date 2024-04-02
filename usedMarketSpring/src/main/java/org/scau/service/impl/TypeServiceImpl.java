package org.scau.service.impl;

import org.apache.ibatis.annotations.Update;
import org.scau.mapper.TypeMapper;
import org.scau.pojo.Type;
import org.scau.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    TypeMapper typeMapper;

    // 添加书籍类型
    @Override
    public void addType(Integer typeID, String typeName) {
        typeMapper.addType(typeID, typeName);
    }

    // 删除书籍类型
    @Override
    public void deleteType(String typeName) {
        typeMapper.deleteType(typeName);
    }

    // 修改书籍类别
    @Override
    public void updateType(Integer oldTypeID, Integer typeID, String typeName){
        typeMapper.updateType(oldTypeID, typeID, typeName);
    }

    // 获取所有书籍类型
    @Override
    public List<Type> getAllType() {
        return typeMapper.getAllType();
    }

    // 根据ID查询类型
    @Override
    public Type getTypeByID(Integer typeID) {
        return typeMapper.getTypeByID(typeID);
    }

    // 根据名称查询类型
    @Override
    public Type getTypeByName(String typeName) {
        return typeMapper.getTypeByName(typeName);
    }
}
