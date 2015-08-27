package com.nova.dao;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.nova.dao.impl.DataTypeDaoImpl;
import com.nova.sdo.DataType;

@ImplementedBy(DataTypeDaoImpl.class)
public interface IDataTypeDao {
    /**
     * 获取数据类型列表
     * 
     * @return
     */
    public List<DataType> getDataTypeList();
}
