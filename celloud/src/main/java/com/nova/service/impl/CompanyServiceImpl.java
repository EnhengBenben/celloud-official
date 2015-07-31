package com.nova.service.impl;

import java.util.List;

import com.google.inject.Inject;
import com.nova.dao.ICompanyDao;
import com.nova.pager.Page;
import com.nova.pager.PageList;
import com.nova.sdo.Company;
import com.nova.service.ICompanyService;

public class CompanyServiceImpl implements ICompanyService{

    @Inject
    private ICompanyDao companyDao;

    @Override
	public int addCompany(Company company) {
		return companyDao.addCompany(company);
    }

    @Override
    public List<Company> getAllCompany() {
        return companyDao.getAllCompany();
    }

    @Override
    public PageList<Company> getCompanyPage(Page page) {
        return companyDao.getCompanyPage(page);
    }

    @Override
	public int updateCompany(Company company) {
		return companyDao.updateCompany(company);
    }

    @Override
	public Company getCompany(int companyId) {
		return companyDao.getCompany(companyId);
    }

	@Override
	public int updateCompany(int companyId, int state) {
		return companyDao.updateCompany(companyId, state);
	}

	@Override
	public Company getCompanyByUserId(int userId) {
		return companyDao.getCompanyByUserId(userId);
	}

}