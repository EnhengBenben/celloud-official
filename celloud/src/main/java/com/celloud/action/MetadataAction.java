package com.celloud.action;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.celloud.model.mysql.Metadata;
import com.celloud.service.MetadataService;

@Controller
@RequestMapping("metadata")
public class MetadataAction {
	Logger logger = LoggerFactory.getLogger(MetadataAction.class);
    @Resource
	private MetadataService ms;

	@RequestMapping("showMetadata")
	@ResponseBody
	public List<Metadata> toShowMetadata(Integer appId, Integer flag) {
		return ms.getMetadata(appId, flag);
    }

}
