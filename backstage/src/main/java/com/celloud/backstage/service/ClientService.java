package com.celloud.backstage.service;


import com.celloud.backstage.model.Client;
import com.celloud.backstage.page.Page;
import com.celloud.backstage.page.PageList;

/**
 * 客户端版本接口
 *
 * @author han
 * @date 2016年2月17日 下午4:09:42
 */
public interface ClientService {
    
    public PageList<Client> getClientByPage(Page page);
    
    public int addClient(Client client);
    
}
