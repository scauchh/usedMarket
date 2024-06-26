package org.scau.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Relation {
    private Integer questionID;    // 密保问题ID
    private Integer userID;        // 用户ID
    private String answer;         // 密保问题答案
}
