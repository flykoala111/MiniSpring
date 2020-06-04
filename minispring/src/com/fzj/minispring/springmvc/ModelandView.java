package com.fzj.minispring.springmvc;

import java.io.Serializable;

/**
 * 模型和视图(公有)
 **/
public class ModelandView implements Serializable {
    private Object data;//数据模型
    private String page;//视图名

    public ModelandView(String page, Object data) {
        this.page = page;
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }
}
