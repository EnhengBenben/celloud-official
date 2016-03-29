package com.celloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.ExperimentState;
import com.celloud.mapper.ExperimentMapper;
import com.celloud.model.mysql.Experiment;
import com.celloud.page.Page;
import com.celloud.page.PageList;
import com.celloud.service.ExperimentService;

@Service("experimentService")
public class ExperimentServiceImpl implements ExperimentService {
	@Resource
	private ExperimentMapper experimentMapper;

	@Override
	public PageList<Experiment> getExperimentPageList(Integer userId, Page page) {
		List<Experiment> list = experimentMapper.getExperimentPageList(userId, ExperimentState.OPEN, page);
		return new PageList<>(page, list);
	}

	@Override
	public int updateByPrimaryKeySelective(Experiment record) {
		return experimentMapper.updateByPrimaryKey(record);
	}

	@Override
	public int insertSelective(Experiment exp) {
		return experimentMapper.insertSelective(exp);
	}

}
