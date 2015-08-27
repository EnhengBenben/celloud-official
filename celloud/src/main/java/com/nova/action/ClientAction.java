package com.nova.action;

import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.google.inject.Inject;
import com.nova.sdo.Client;
import com.nova.service.IClientService;

@ParentPackage("celloud-default")
@Action("client")
@Results({
	@Result(name = "all", location = "../../pages/admin/clientList.jsp"),
	@Result(name = "success", type = "json", params = { "root", "flag" }),
	@Result(name = "last", type = "json", params = { "root", "client" }) })
public class ClientAction extends BaseAction {

    private static final long serialVersionUID = 1L;
    @Inject
    private IClientService clientService;
    private List<Client> list;
    private Client client;
    private Integer flag;

    /**
     * 新增客户端版本信息
     * 
     * @return
     */
    public String add() {
	flag = clientService.add(client);
	return SUCCESS;
    }

    /**
     * 获取所有的客户端版本信息
     * 
     * @return
     */
    public String getAll() {
	list = clientService.getAll();
	return "all";
    }

    /**
     * 获取最新的客户端版本信息
     * 
     * @return
     */
    public String getLast() {
	client = clientService.getLast();
	return "last";
    }

    public List<Client> getList() {
	return list;
    }

    public void setList(List<Client> list) {
	this.list = list;
    }

    public Client getClient() {
	return client;
    }

    public void setClient(Client client) {
	this.client = client;
    }

    public Integer getFlag() {
	return flag;
    }

    public void setFlag(Integer flag) {
	this.flag = flag;
    }

}