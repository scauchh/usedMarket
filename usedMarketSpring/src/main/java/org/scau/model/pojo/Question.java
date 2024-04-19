package org.scau.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    private Integer questionID;     // 密保问题ID
    private String item;            // 密保问题内容
}
