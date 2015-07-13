package com.nova.service.impl;

import java.util.List;

import com.google.inject.Inject;
import com.nova.dao.IClientDao;
import com.nova.sdo.Client;
import com.nova.service.IClientService;

public class ClientServiceImpl implements IClientService {
	@Inject
	private IClientDao clientDao;

	@Override
	public int add(Client client) {
		return clientDao.add(client);
	}

	@Override
	public Client getLast() {
		return clientDao.getLast();
	}

	@Override
	public List<Client> getAll() {
		return clientDao.getAll();
	}
}