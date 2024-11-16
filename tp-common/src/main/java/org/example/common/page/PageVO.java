package org.example.common.page;

import lombok.Data;

import java.util.List;

@Data
public class PageVO<T> {
    protected List<T> items;
    protected long total;

    public PageVO(List<T> items, long total) {
        this.items = items;
        this.total = total;
    }

    public PageVO() {
    }
}