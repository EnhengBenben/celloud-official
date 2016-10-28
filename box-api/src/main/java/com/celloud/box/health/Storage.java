package com.celloud.box.health;

import java.io.File;
import java.text.DecimalFormat;

import javax.annotation.Resource;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

import com.celloud.box.config.BoxConfig;

@Component
public class Storage implements HealthIndicator {
	@Resource
	private BoxConfig config;

	@Override
	public Health health() {
		File file = new File(config.getUploadPath());
		DecimalFormat format = new DecimalFormat("##.##%");
		double rate = (double) file.getFreeSpace() / (double) file.getTotalSpace();
		return Health.up().withDetail("free", file.getFreeSpace()).withDetail("total", file.getTotalSpace())
				.withDetail("rate", format.format(rate)).build();
	}

}
