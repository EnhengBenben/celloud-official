package com.celloud.backstage.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.backstage.mapper.ClientMapper;
import com.celloud.backstage.model.Client;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;
import com.celloud.backstage.service.ClientService;

@Service("clientService")
public class ClientServiceImpl implements ClientService {
    @Resource
    private ClientMapper clientMapper;

    @Override
    public PageList<Client> getClientByPage(Page page) {
        List<Client> list=clientMapper.getClientByPage(page);
        return new PageList<Client>(page,list);
    }

    @Override
    public int addClient(Client client) {
        if(client!=null){
            return clientMapper.addClient(client);
        }
        return 0;
    }
    
}
