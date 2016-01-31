package com.celloud.mapper;

import com.celloud.model.Client;

public interface ClientMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Client record);

    int insertSelective(Client record);

    Client selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Client record);

    int updateByPrimaryKey(Client record);
    
    /**
     * 获取最新的客户端版本
     * 
     * @return
     * @author lin
     * @date 2016年1月31日下午10:03:37
     */
    Client getLast();
}