package com.celloud.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import com.celloud.constants.SampleTypes;
import com.celloud.model.mysql.Metadata;
import com.celloud.service.MetadataService;

/**
 * 初始化实验管理元数据
 * 
 * @author lin
 * @date 2016年11月1日 上午10:11:45
 */
@Service
public class MetaDataListener implements ApplicationListener<ContextRefreshedEvent> {
	Logger logger = LoggerFactory.getLogger(MetaDataListener.class);
	@Resource
	private MetadataService metadataService;

	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if (event.getApplicationContext().getParent() == null) {
			//样本index
			List<Metadata> sampleIndex = metadataService.getMetadata(118, 1);
			List<String> sampleList = new ArrayList<>();
			for (Metadata metadata : sampleIndex) {
				sampleList.add(metadata.getName() + ":" + metadata.getSeq());
			}
			SampleTypes.index = sampleList;
			//文库index
			List<Metadata> libraryIndex = metadataService.getMetadata(118, 2);
			SampleTypes.libraryIndex = libraryIndex;
			//样本类型
			List<Metadata> sampleType = metadataService.getMetadata(118, 3);
			Map<String, String> typeList = new HashMap<>();
			for (Metadata metadata : sampleType) {
				typeList.put(metadata.getName(), metadata.getSeq());
			}
			SampleTypes.types = typeList;
			logger.info("实验元数据初始化完毕");
		}
	}
}
