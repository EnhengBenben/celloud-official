package com.celloud.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.celloud.constants.DataState;
import com.celloud.mapper.MetadataMapper;
import com.celloud.model.mysql.Metadata;
import com.celloud.service.MetadataService;

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
    public List<Map<String, String>> getMetadataToSelectByTagIdAndFlag(Integer tagId, Integer flag) {
        return mm.getMetadataToSelectByTagIdAndFlag(tagId, flag);
    }

    @Override
    public Map<String, Map<String, String>> getNameSeqMap(Integer flag) {
        return mm.getNameSeqMap(flag);
    }

}
