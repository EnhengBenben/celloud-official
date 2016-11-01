package com.celloud.action;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.celloud.constants.SampleTypes;
import com.celloud.model.mysql.Metadata;
import com.celloud.service.MetadataService;

@Controller
@RequestMapping("metadata")
public class MetadataAction {
	Logger logger = LoggerFactory.getLogger(MetadataAction.class);
    @Resource
	private MetadataService ms;

	/**
	 * 根据appId和flag动态获取元数据
	 * 
	 * @param appId
	 * @param flag
	 * @return
	 * @author lin
	 * @date 2016年11月1日上午10:36:48
	 */
	@RequestMapping("showMetadata")
	@ResponseBody
	public List<Metadata> showMetadata(Integer appId, Integer flag) {
		return ms.getMetadata(appId, flag);
    }

	/**
	 * 获取系统初始化好的文库index
	 * 
	 * @return
	 * @author lin
	 * @date 2016年11月1日上午10:37:06
	 */
	@RequestMapping("libraryIndex")
	@ResponseBody
	public List<Metadata> libraryIndex() {
		return SampleTypes.libraryIndex;
	}

}
