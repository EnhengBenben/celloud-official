package com.celloud.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.celloud.utils.EmailUtils;

/**
 * 全局异常处理器
 * 
 * @author <a href="sunwendong@celloud.cn">sun8wd</a>
 * @date 2015年12月23日 下午4:11:36
 */
public class ExceptionHandler implements HandlerExceptionResolver {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception exception) {
        EmailUtils.sendError(request, exception);
        if (exception instanceof BusinessException) {
            return new ModelAndView("errors/business").addObject("exception", exception);
        }
        logger.error("系统出现未捕获的异常！", exception);
        return new ModelAndView("errors/error").addObject("exception", exception);
    }

}
