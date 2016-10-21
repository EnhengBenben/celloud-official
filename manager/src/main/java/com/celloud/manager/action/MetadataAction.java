package com.celloud.manager.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.manager.constants.DataState;
import com.celloud.manager.model.Metadata;
import com.celloud.manager.service.MetadataService;

@Controller
@RequestMapping("metadata")
public class MetadataAction {
	Logger logger = LoggerFactory.getLogger(MetadataAction.class);
    @Resource
	private MetadataService ms;

	@RequestMapping("deleteMetadata")
	@ResponseBody
	public Integer deleteMetadata(Integer id) {
		Metadata metadata = new Metadata();
		metadata.setId(id);
		metadata.setExpireDate(new Date());
		metadata.setState(DataState.DEELTED);
		return ms.updateByPrimaryKeySelective(metadata);
	}

	@RequestMapping("toShowMetadata")
	public ModelAndView toShowMetadata(Integer appId, Integer flag) {
		ModelAndView mv = new ModelAndView("metadata/metadata_list");
		List<Metadata> metadataList = ms.getMetadata(appId, flag);
		mv.addObject("metadataList", metadataList);
        return mv;
    }

	@RequestMapping("updateMetadata")
    @ResponseBody
	public Integer updateMetadata(Metadata metadata) {
		if (metadata.getId() == null) {
			metadata.setState(DataState.ACTIVE);
			metadata.setCreateDate(new Date());
			Integer max = ms.getMaxPriority(metadata.getAppId(), metadata.getFlag());
			max = max == null ? 1 : max + 1;
			metadata.setPriority(max);
			return ms.insertSelective(metadata);
		}
		return ms.updateByPrimaryKeySelective(metadata);
    }

	@RequestMapping("moveUp")
	@ResponseBody
	public Integer moveUp(Integer id) {
		Metadata m1 = ms.selectByPrimaryKey(id);
		Metadata m2 = ms.selectUp(m1);
		return ms.updateMove(m1, m2);
	}

	@RequestMapping("moveDown")
	@ResponseBody
	public Integer moveDown(Integer id) {
		Metadata m1 = ms.selectByPrimaryKey(id);
		Metadata m2 = ms.selectDown(m1);
		return ms.updateMove(m1, m2);
	}

	/**
	 * 
	 * 
	 * @param metadata
	 * @return
	 * @author lin
	 * @date 2016年10月19日上午11:28:19
	 */
	@RequestMapping("checkRepeat")
	@ResponseBody
	public Integer checkRepeat(Metadata metadata) {
		return ms.checkRepeat(metadata);
	}
}
