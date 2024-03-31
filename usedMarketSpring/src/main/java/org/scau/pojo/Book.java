package org.scau.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private Integer bookID;  // 书籍ID
    private Integer userID;  // 用户ID
    private String title;    // 书籍标题
    private String picture;  // 书籍图片
    private Double price;    // 书籍价格
    private String type;     // 书籍类型
    private String notes;    // 备注
}
