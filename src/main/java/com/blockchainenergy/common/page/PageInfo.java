package com.blockchainenergy.common.page;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageSerializable;
import lombok.Data;

import java.util.Collection;
import java.util.List;

/**
 * 用于输出
 */
@Data
public class PageInfo<T> extends PageSerializable<T> {
    private int pageNum;//页码
    private int pageSize;//每页记录数
    private int pages;// 总页数

    public PageInfo(List<T> list) {
        super(list);
        if (list instanceof Page) {
            Page page = (Page) list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.pages = page.getPages();
        } else if (list instanceof Collection) {
            this.pageNum = 1;
            this.pageSize = list.size();
            this.pages = this.pageSize > 0 ? 1 : 0;
        }
    }
}
