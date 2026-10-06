package com.itheima.dto;
import lombok.Data;

@Data
public class Result<T> {

    // 状态码 0失败 1成功
    private Integer code;

    // 失败时返回的错误信息
    private String msg;

    // 成功是返回的数据
    private T data;

    public static <T> Result<T> success() {
        Result<T> successResult = new Result<>();
        successResult.setCode(1);
        successResult.setMsg("操作成功");
        successResult.setData(null);
        return successResult;
    }

    public static <T> Result<T> success(T data) {
        Result<T> successResult = new Result<>();
        successResult.setCode(1);
        successResult.setMsg("操作成功");
        successResult.setData(data);
        return successResult;
    }

    /**
     * 失败返回
     * @param msg
     * @return
     */
    public static <T> Result<T> fail(String msg) {
        Result<T> failResult = new Result<>();
        failResult.setCode(0);
        failResult.setMsg(msg);
        failResult.setData(null);
        return failResult;
    }

}