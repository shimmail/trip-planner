package com.example.common.result;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.Serializable;

/**
 * 后端统一返回结果
 */
@Data
public class Result<T> implements Serializable {

    private Integer code; // 编码：1成功，0和其它数字为失败
    private String msg; // 错误信息
    private T data; // 数据

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<>();
        result.setCode(1);
        result.setMsg("success");
        result.setData(object);
        return result;
    }
    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(1);
        result.setMsg("success");
        return result;
    }
    public static <T> Result<T> error(String msg) {
        Result<T> result = new Result<>();
        result.setCode(0);
        result.setMsg(msg);
        return result;
    }

    public T getData() {
        return data;
    }
}
