package com.nova.dao;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.nova.dao.impl.ClientDaoImpl;
import com.nova.sdo.Client;

@ImplementedBy(ClientDaoImpl.class)
public interface IClientDao {
    /**
     * 新增客户端版本
     * 
     * @param client
     * @return
     */
    int add(Client client);

    /**
     * 获取最新的客户端版本
     * 
     * @return
     */
    Client getLast();

    /**
     * 获取所有的版本信息
     * 
     * @return
     */
    List<Client> getAll();
}