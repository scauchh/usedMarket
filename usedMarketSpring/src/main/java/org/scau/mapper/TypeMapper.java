package org.scau.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.scau.pojo.Type;

import java.util.List;

@Mapper
public interface TypeMapper {
    @Select("SELECT * FROM type;")
    List<Type> getAllType();
}
