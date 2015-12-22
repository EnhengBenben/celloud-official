package com.celloud.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class ExceptionHandler implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception exception) {
		//log exception
		// mail to programmer
		if(exception instanceof BusinessException){
			return new ModelAndView("errors/business").addObject("exception",exception);
		}
		return new ModelAndView("errors/error").addObject("exception",exception);
	}

}
