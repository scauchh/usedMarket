package org.scau.mapper;

import org.apache.ibatis.annotations.*;
import org.scau.pojo.Type;

import java.util.List;

@Mapper
public interface TypeMapper {
    // 添加一个类型
    @Insert("INSERT INTO type VALUES (#{typeID}, #{typeName})")
    void addType(Integer typeID, String typeName);

    // 根据名称删除一个类型
    @Delete("DELETE FROM type WHERE type_name = #{typeName}")
    void deleteType(String typeName);

    // 修改书籍类别
    @Update("UPDATE type SET type_id = #{typeID}, type_name = #{typeName} WHERE type_id = #{oldTypeID}")
    void updateType(Integer oldTypeID, Integer typeID, String typeName);

    // 查询所有类型
    @Select("SELECT * FROM type ORDER BY type_id;")
    List<Type> getAllType();

    // 根据ID查询类型
    @Select("SELECT * FROM type WHERE type_id = #{typeID};")
    Type getTypeByID(Integer typeID);

    // 根据名称查询类型
    @Select("SELECT * FROM type WHERE type_name = #{typeName};")
    Type getTypeByName(String typeName);
}
