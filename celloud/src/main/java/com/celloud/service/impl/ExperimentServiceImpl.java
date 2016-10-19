package com.celloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.DataState;
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
		return experimentMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int insertSelective(Experiment exp) {
		return experimentMapper.insertSelective(exp);
	}

	@Override
	public PageList<Experiment> getExpDonePageList(Integer userId, Page page, Integer sampleId, Integer methodId,
			Integer stepId, String start, String end) {
		List<Experiment> list = experimentMapper.getExpDonePageList(userId, ExperimentState.OPEN, page, sampleId,
				methodId, stepId, start, end);
		return new PageList<>(page, list);
	}

	@Override
	public Experiment selectByPrimaryKey(Integer id) {
		return experimentMapper.selectByPrimaryKey(id);
	}

	@Override
	public Integer checkoutNumber(Integer userId, String num) {
		return experimentMapper.checkoutNumber(userId, ExperimentState.OPEN, num);
	}

	@Override
	public List<Experiment> getUnRelatList(Integer userId, String number) {
		return experimentMapper.getUnRelatList(userId, ExperimentState.OPEN, number, ExperimentState.RELAT_STEP,
				ExperimentState.UN_RELAT_FILEID);
	}

	@Override
	public List<Experiment> getRelatList(Integer userId, String number, String dataKey) {
		return experimentMapper.getRelatList(userId, ExperimentState.OPEN, number, ExperimentState.RELAT_STEP, dataKey);
	}

	@Override
	public List<Experiment> getReportList(Integer userId, String dataKey, Integer appId) {
		return experimentMapper.getReportList(userId, ExperimentState.REPORT_STEP, dataKey, appId);
	}

	@Override
	public Integer getApp(Integer sample, Integer method, Integer sequenator) {
		return experimentMapper.getApp(sample, method, sequenator, DataState.ACTIVE);
	}

}
