package com.celloud.box.health;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.boot.actuate.info.Info.Builder;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.stereotype.Component;

import com.celloud.box.config.BoxConfig;
import com.celloud.box.utils.LocalIpAddressUtil;

@Component
public class BoxInfoContributor implements InfoContributor {
	@Resource
	private BoxConfig config;

	@Override
	public void contribute(Builder builder) {
		Map<String, Object> infos = new HashMap<>();
		infos.put("ip", LocalIpAddressUtil.getLocalArress(config.getNetwork()));
		infos.put("name", config.getNetwork());
		builder.withDetail("network", infos);
	}

}
