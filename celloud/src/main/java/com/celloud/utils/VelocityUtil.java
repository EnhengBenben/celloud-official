package com.celloud.utils;

import java.io.StringWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.tools.generic.DateTool;
import org.apache.velocity.tools.generic.NumberTool;

import com.celloud.constants.ConstantsData;

public class VelocityUtil {
	@Resource
	private VelocityEngine velocityEngine;
	private static final String MAIL_TEMPLATE_PATH = "templates/mail";
	private static final String REPORT_TEMPLATE_PATH = "templates/report";

	public String mergeMailTemplate(String template, Map<String, Object> context) {
		if (!template.startsWith("/")) {
			template = "/" + template;
		}
		template = MAIL_TEMPLATE_PATH + template;
		return mergeTemplate(template, context);
	}

	public String mergeReportTemplate(String template, Map<String, Object> context) {
		if (!template.startsWith("/")) {
			template = "/" + template;
		}
		template = REPORT_TEMPLATE_PATH + template;
		return mergeTemplate(template, context);
	}

	private String mergeTemplate(String template, Map<String, Object> context) {
		if (context == null) {
			context = new HashMap<>();
		}
		Template temp = velocityEngine.getTemplate(template, "utf-8");
		StringWriter writer = new StringWriter();
		context.put("dateTool", new DateTool());
		context.put("numberTool", new NumberTool());
		context.put("base", ConstantsData.getRequset().getContextPath());
		context.put("rightDate", new Date());
		temp.merge(new VelocityContext(context), writer);
		return writer.toString();
	}

	public VelocityEngine getVelocityEngine() {
		return velocityEngine;
	}

	public void setVelocityEngine(VelocityEngine velocityEngine) {
		this.velocityEngine = velocityEngine;
	}

}
