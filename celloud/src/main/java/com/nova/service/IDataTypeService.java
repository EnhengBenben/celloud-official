package com.nova.service;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.nova.sdo.DataType;
import com.nova.service.impl.DataTypeServiceImpl;

@ImplementedBy(DataTypeServiceImpl.class)
public interface IDataTypeService {
    /**
     * 获取数据类型列表
     * 
     * @return
     */
    public List<DataType> getDataTypeList();
}
