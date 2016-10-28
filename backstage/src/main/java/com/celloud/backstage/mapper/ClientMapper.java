package com.celloud.backstage.mapper;

import java.util.List;

import com.celloud.backstage.model.Client;
import com.celloud.backstage.page.Page;

public interface ClientMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Client record);

    int insertSelective(Client record);

    Client selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Client record);

    int updateByPrimaryKey(Client record);
    
    public List<Client> getClientByPage(Page page);
    
    public int addClient(Client client);
    
}