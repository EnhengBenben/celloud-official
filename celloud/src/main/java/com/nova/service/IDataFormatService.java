package com.nova.service;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.nova.sdo.DataFormat;
import com.nova.service.impl.DataFormatServiceImpl;

@ImplementedBy(DataFormatServiceImpl.class)
public interface IDataFormatService {
    /**
     * 获取数据类型列表
     * 
     * @return
     */
    public List<DataFormat> getDataFormatList();

    /**
     * 根据类型名称获取类型编号
     * 
     * @param typeName
     * @return
     */
    public int getDataFormatIdByName(String formatName);

    /**
     * 根据类型编号获取类型名称
     * 
     * @param typeName
     * @return
     */
    public String getDataFormatNameById(int dataFormatId);
}
