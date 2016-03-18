package com.celloud.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.mapper.ClientMapper;
import com.celloud.model.mysql.Client;
import com.celloud.service.ClientService;

@Service("clientService")
public class ClientServiceImpl implements ClientService{
	@Resource
	private ClientMapper clientMapper;

	@Override
	public Client getLast() {
		return clientMapper.getLast();
	}

}
