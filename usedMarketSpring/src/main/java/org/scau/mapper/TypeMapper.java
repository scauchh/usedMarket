package org.scau.mapper;

import org.apache.ibatis.annotations.*;
import org.scau.model.pojo.Type;

import java.util.List;

@Mapper
public interface TypeMapper {
    // 添加类别
    @Insert("INSERT INTO type VALUES (#{typeID}, #{typeName})")
    void addType(Integer typeID, String typeName);

    // 根据类别名称删除类别
    @Delete("DELETE FROM type WHERE type_name = #{typeName}")
    void deleteType(String typeName);

    // 修改物品类别
    @Update("UPDATE type SET type_id = #{typeID}, type_name = #{typeName} WHERE type_id = #{oldTypeID}")
    void updateType(Integer oldTypeID, Integer typeID, String typeName);

    // 查询所有类别
    @Select("SELECT * FROM type ORDER BY type_id")
    List<Type> getAllType();

    // 根据类别编号查询类别
    @Select("SELECT * FROM type WHERE type_id = #{typeID}")
    Type getTypeByID(Integer typeID);

    // 根据类别名称查询类别
    @Select("SELECT * FROM type WHERE type_name = #{typeName}")
    Type getTypeByName(String typeName);
}
