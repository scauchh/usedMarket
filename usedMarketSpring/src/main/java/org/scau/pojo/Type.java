package org.scau.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Type {
    private Integer typeID;    // 物品类型ID
    private String typeName;   // 物品类型名称
}
