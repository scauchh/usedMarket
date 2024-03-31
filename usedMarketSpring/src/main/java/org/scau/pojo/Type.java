package org.scau.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Type {
    private Integer typeID;    // 书籍类型ID
    private String typeName;   // 书籍类型名称
}
