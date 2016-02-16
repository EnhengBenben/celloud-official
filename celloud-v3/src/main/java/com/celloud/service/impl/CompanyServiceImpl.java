package com.celloud.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.mapper.CompanyMapper;
import com.celloud.model.Company;
import com.celloud.service.CompanyService;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {
	@Resource
	private CompanyMapper cm;

	@Override
	public Company selectByPrimaryKey(Integer companyId) {
		return cm.selectByPrimaryKey(companyId);
	}

}
