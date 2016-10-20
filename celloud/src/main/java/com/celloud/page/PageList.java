package com.celloud.page;

import java.util.List;

/**
 * 分页结果对象
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2016年1月4日 下午2:03:20
 */
public class PageList<T> {
    private Page page;
    private List<T> datas;

    public PageList() {

    }

    public PageList(Page page, List<T> datas) {
        this.page = page;
        this.datas = datas;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public List<T> getDatas() {
        return datas;
    }

    public void setDatas(List<T> datas) {
        this.datas = datas;
    }

}
