package com.celloud.box.action;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.celloud.box.constants.Response;

@RestController
public class ErrorHandleController implements ErrorController {
	private static final String ERROR_PATH = "/error";
	private static Logger logger = LoggerFactory.getLogger(ErrorHandleController.class);

	@RequestMapping(ERROR_PATH)
	public Response errorHandle(HttpServletResponse response) {
		logger.info(response.getStatus() + "");
		return new Response(response.getStatus() + "", "error");
	}

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}

}
