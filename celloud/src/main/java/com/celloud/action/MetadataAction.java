package com.celloud.action;

import java.util.List;
import java.util.Map;

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
     * 
     * @description 根据tagId获取元数据返回给select2
     * @author miaoqi
     * @date 2017年2月13日 下午3:59:33
     * @param tagId
     * @param flag
     * @return
     */
    @RequestMapping("listMetadataToSelectByTagIdAndFlag")
    @ResponseBody
    public List<Map<String, String>> listMetadataToSelectByTagIdAndFlag(Integer tagId, Integer flag) {
        if (tagId == null || flag == null) {
            logger.info("参数有误, 不能获取元数据 tagId = {}, flag = {}", tagId, flag);
            return null;
        }
        // 根据tagId获取appId
        return ms.getMetadataToSelectByTagIdAndFlag(tagId, flag);
    }

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

	/**
	 * 获取系统初始化好的样本类型
	 * 
	 * @return
	 * @author lin
	 * @date 2016年11月1日下午1:23:33
	 */
	@RequestMapping("sampleType")
	@ResponseBody
	public List<Metadata> sampleType() {
		return SampleTypes.types;
	}

}
