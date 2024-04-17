package org.scau.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuppressWarnings("all")
public class Result<T> {
    private Integer code;    // 状态码（0表示成功，1表示失败）
    private String message;  // 提示信息
    private T data;          // 携带的数据

    // 返回带数据的成功结果
    public static <E> Result success(E data) {
        return new Result<>(0, "成功", data);
    }

    // 返回不带数据的成功结果
    public static Result success() {
        return new Result<>(0, "成功", null);
    }

    // 返回失败结果
    public static Result error(String message) {
        return new Result<>(1, message, null);
    }
}
