package com.brain.base.result;

import lombok.Data;

import java.io.Serializable;

//几个参数封装
@Data
public class PageTableRequest implements Serializable {

    private Integer page;  //第几页
    private Integer limit; //每页多少数据
    private Integer offset; //偏移量

    public void countOffset(){
        if(null == this.page || null == this.limit){
            this.offset = 0;
            return;
        }
        this.offset = (this.page - 1) * limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }
}
