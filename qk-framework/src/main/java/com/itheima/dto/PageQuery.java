package com.itheima.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

@Data
public class PageQuery {
    private Integer page = 1;
    private Integer pageSize = 10;

    public <T> Page<T> buildPage() {
        //前端可能传空值（如 page=&pageSize=），此处兜底避免拆箱空指针
        long current = this.page == null || this.page < 1 ? 1 : this.page;
        long size = this.pageSize == null || this.pageSize < 1 ? 10 : this.pageSize;
        return new Page<>(current, size);
    }
}
