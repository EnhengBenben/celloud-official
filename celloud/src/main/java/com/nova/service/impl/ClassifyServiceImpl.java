package com.nova.service.impl;

import java.util.List;

import com.google.inject.Inject;
import com.nova.dao.IClassifyDao;
import com.nova.sdo.Classify;
import com.nova.sdo.Software;
import com.nova.service.IClassifyService;
import com.nova.utils.Page;

public class ClassifyServiceImpl implements IClassifyService {
	@Inject
	private IClassifyDao classifyDao;

	@Override
	public int createClassify(Classify classify) {
		return classifyDao.createClassify(classify);
	}

	@Override
	public int deleteClassify(int classifyId) {
		return classifyDao.deleteClassify(classifyId);
	}

	@Override
	public int updateClassify(Classify classify) {
		return classifyDao.updateClassify(classify);
	}

	@Override
	public Classify getClassify(int classifyId) {
		return classifyDao.getClassify(classifyId);
	}

	@Override
	public List<Classify> getAllClassifyList() {
		return classifyDao.getAllClassifyList();
	}

	@Override
	public List<Classify> getPageClassify(Page page) {
		return classifyDao.getPageClassify(page);
	}

	@Override
	public int getTotalClassify() {
		return classifyDao.getTotalClassify();
	}

	@Override
	public List<Classify> selectChildNode(int classifyId) {
		return classifyDao.selectChildNode(classifyId);
	}

	@Override
	public List<Software> selectChildSoft(int classifyId) {
		return classifyDao.selectChildSoft(classifyId);
	}

	@Override
	public String selectClassifyName(String ClassifyName) {
		return classifyDao.selectClassifyName(ClassifyName);
	}
	
	@Override
	public List<Classify> getAllSubClassifyList() {
		return classifyDao.getAllSubClassifyList();
	}
}