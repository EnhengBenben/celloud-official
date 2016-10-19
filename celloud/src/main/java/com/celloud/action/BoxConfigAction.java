package com.celloud.action;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.celloud.constants.ConstantsData;
import com.celloud.model.mysql.BoxConfig;
import com.celloud.service.BoxConfigService;
import com.celloud.utils.UserAgentUtil;

@RestController
@RequestMapping("box")
public class BoxConfigAction {
	@Resource
	private BoxConfigService boxConfigService;

	@RequestMapping("configs")
	public List<BoxConfig> configs(HttpServletRequest request) {
		return boxConfigService.selectByUserId(ConstantsData.getLoginUserId(), UserAgentUtil.getIp(request));
	}
}
