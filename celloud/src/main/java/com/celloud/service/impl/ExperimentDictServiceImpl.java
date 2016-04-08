package com.celloud.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.DataState;
import com.celloud.mapper.ExperimentDictMapper;
import com.celloud.model.mysql.ExperimentDict;
import com.celloud.service.ExperimentDictService;

@Service("experimentDictService")
public class ExperimentDictServiceImpl implements ExperimentDictService {
	@Resource
	private ExperimentDictMapper edm;

	@Override
	public List<ExperimentDict> getExperimentDictList() {
		return edm.getExperimentDictList(DataState.ACTIVE);
	}

}
