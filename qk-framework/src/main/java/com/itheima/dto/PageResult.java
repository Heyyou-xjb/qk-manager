package com.itheima.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;


/**
 * 分页结果封装类
 * 用于封装分页查询的结果，包含总记录数和当前页的数据列表
 */
@Data
public class PageResult<T> {

    // 总记录数
    private long total;

    // 当前页的数据列表
    private List<T> rows;

    // 返回空数据
    public static <T> PageResult<T> empty() {
        PageResult<T> result = new PageResult<>();
        result.setTotal(0);
        result.setRows(new ArrayList<>());
        return result;
    }

    // 构建PageResult
    public static <T> PageResult<T> of(List<T> dataList, long total) {
        PageResult<T> result = new PageResult<>();
        result.setTotal(total);
        result.setRows(dataList);
        return result;
    }
}
