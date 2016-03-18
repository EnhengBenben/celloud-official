package com.celloud.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.mapper.DeptMapper;
import com.celloud.model.mysql.Dept;
import com.celloud.service.DeptService;

@Service("deptService")
public class DeptServiceImpl implements DeptService {
	@Resource
	DeptMapper deptMapper;

	@Override
	public Dept selectByPrimaryKey(Integer deptId) {
		return deptMapper.selectByPrimaryKey(deptId);
	}

}
