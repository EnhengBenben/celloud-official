package com.celloud.exception;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.alimail.AliEmail;
import com.celloud.alimail.AliEmailUtils;
import com.celloud.alimail.AliSubstitution;
import com.celloud.constants.ConstantsData;
import com.celloud.mail.EmailUtils;
import com.celloud.sendcloud.EmailParams;
import com.celloud.sendcloud.EmailType;
import com.celloud.utils.SlackBot;

/**
 * 全局异常处理器
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月23日 下午4:11:36
 */
public class ExceptionHandler implements HandlerExceptionResolver {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	private EmailUtils emailUtils;
	@Resource
	private AliEmailUtils aliEmailUtils;

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) {
		logger.error("系统出现未捕获的异常:" + request.getRequestURI(), exception);
		String errorInfo = emailUtils.getError(request, exception);
		AliEmail aliEmail = AliEmail.template(EmailType.EXCEPTION).substitutionVars(
				AliSubstitution.sub().set(EmailParams.EXCEPTION.home.name(), ConstantsData.getContextUrl())
						.set(EmailParams.EXCEPTION.context.name(), errorInfo));
		aliEmailUtils.simpleSend(aliEmail, emailUtils.getErrorMailTo());
		response.setHeader("exceptionstatus", "exception");
		SlackBot.error(request, exception);
		if (exception instanceof BusinessException) {
			return new ModelAndView("errors/business").addObject("exception", exception);
		}
		return new ModelAndView("errors/error").addObject("exception", exception);
	}

}
