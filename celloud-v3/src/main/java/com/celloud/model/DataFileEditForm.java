package com.celloud.model;

import java.util.List;

/**
 * 批量保存数据表单数据
 * 
 * @author leamo
 * @date 2016-1-11 下午3:18:55
 */
public class DataFileEditForm {
    private List<DataFile> dataList;

    public List<DataFile> getDataList() {
        return dataList;
    }

    public void setDataList(List<DataFile> dataList) {
        this.dataList = dataList;
    }
}
