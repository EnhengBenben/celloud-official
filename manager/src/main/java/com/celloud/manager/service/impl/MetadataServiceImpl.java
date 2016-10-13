package com.celloud.manager.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.manager.constants.DataState;
import com.celloud.manager.mapper.MetadataMapper;
import com.celloud.manager.model.Metadata;
import com.celloud.manager.service.MetadataService;

@Service("metadataService")
public class MetadataServiceImpl implements MetadataService {

	@Resource
	private MetadataMapper mm;

	@Override
	public int insertSelective(Metadata record) {
		return mm.insertSelective(record);
	}

	@Override
	public Metadata selectByPrimaryKey(Integer id) {
		return mm.selectByPrimaryKey(id);
	}

	@Override
	public int updateByPrimaryKeySelective(Metadata record) {
		return mm.updateByPrimaryKeySelective(record);
	}

	@Override
	public List<Metadata> getMetadata(Integer appId, Integer flag) {
		return mm.getMetadata(appId, flag, DataState.ACTIVE);
	}

	@Override
	public Integer getMaxPriority(Integer appId, Integer flag) {
		return mm.getMaxPriority(appId, flag);
	}

	@Override
	public Integer updateMove(Metadata d1, Metadata d2) {
		Integer priority = d1.getPriority();
		d1.setPriority(d2.getPriority());
		d2.setPriority(priority);
		Integer r1 = mm.updateByPrimaryKeySelective(d1);
		Integer r2 = mm.updateByPrimaryKeySelective(d2);
		return r1 + r2;
	}

	@Override
	public Metadata selectUp(Metadata meta) {
		return mm.selectUp(meta);
	}

	@Override
	public Metadata selectDown(Metadata meta) {
		return mm.selectDown(meta);
	}

}
