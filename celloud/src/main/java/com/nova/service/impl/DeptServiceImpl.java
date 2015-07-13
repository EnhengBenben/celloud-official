package com.nova.service.impl;

import java.util.List;

import com.google.inject.Inject;
import com.nova.dao.IDeptDao;
import com.nova.sdo.Dept;
import com.nova.service.IDeptService;

public class DeptServiceImpl implements IDeptService {
	@Inject
	private IDeptDao dd;

	@Override
	public List<Dept> getDeptAll(Integer companyId) {
		return dd.getDeptAll(companyId);
	}

	@Override
	public Dept getDept(Integer deptId) {
		return dd.getDept(deptId);
	}

	@Override
	public Integer add(Dept dept) {
		return dd.add(dept);
	}

	@Override
	public Integer updateState(Dept dept) {
		return dd.updateState(dept);
	}

	@Override
	public Integer updateDept(Dept dept) {
		return dd.updateDept(dept);
	}

}
